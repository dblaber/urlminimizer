/*******************************************************************************
 * Copyright 2016 Darren Blaber
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *******************************************************************************/
package org.da4.urlminimizer.plugins;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.da4.urlminimizer.Hook;
import org.da4.urlminimizer.Operation;
import org.da4.urlminimizer.plugins.sql.IJDBCDAO;
import org.da4.urlminimizer.plugins.sql.PSQLDAO;

public class NonBlockingStatsPlugin extends PluginAPI {
	private ConcurrentLinkedQueue<StatsRequestVO> statQueue = null;
	private static final Logger logger = LogManager.getLogger(NonBlockingStatsPlugin.class);
	private IJDBCDAO dao = null;
	class WorkerThread implements Runnable
	{
		private int threadId;
		private LinkedBlockingQueue<StatsRequestVO> statQueue;
		private IJDBCDAO dao = null;
		public WorkerThread(int threadId,LinkedBlockingQueue<StatsRequestVO> statQueue, IJDBCDAO dao) {
			this.threadId = threadId;
			this.statQueue = statQueue;
			this.dao = dao;
		}
		
		@Override
		public void run() {
			Thread.currentThread().setName("Minimizer_Stats_thread_" + threadId);
			try {
				StatsRequestVO statsReq = statQueue.take();
				logger.debug("Stats thread awakens!");
				//determine if its url creation, because then we will only insert click column
				dao.insertNewClicksCount(statsReq.getAlias(), new Date());
				// first log
				logger.debug("Updating stats for alias" + statsReq.getAlias());
				dao.insertStatsLog(statsReq.getAlias(), statsReq.getIp(), statsReq.getUserAgent(), statsReq.getReferrer(), new Date());
				dao.incrementClickCount(statsReq.getAlias(), new Date());
				
			} catch (Exception e) {
				logger.error("Worker thread Exception",e);
			}
			
		}
	}
	
	@Override
	public void init(Map<String, String> params) {
		super.init(params);
		dao = new PSQLDAO(params.get("url"), params.get("userid"), params.get("password"));
		LinkedBlockingQueue<StatsRequestVO> statQueue = new LinkedBlockingQueue<StatsRequestVO>();
		int workerCount = 4;
		if(params.get("WorkerCount") != null)
			workerCount = Integer.parseInt(params.get("WorkerCount"));
		ExecutorService executor = Executors.newFixedThreadPool(workerCount);
		//set up worker threads
		for(int i=0; i < workerCount; i++)
		{
			WorkerThread worker = new WorkerThread(i, statQueue,dao);
			executor.execute(worker);
		}
	}

	@Override
	public Object execute(Hook hook, Operation operation, Object input, Object output, Map<String, Object> params) {
		Map<String,String> clientMetadata = (Map<String,String>)params.get("CLIENT_METADATA");
		// if null lets just create empty map to avoid null checks later
		if(clientMetadata == null)
			clientMetadata = new HashMap<String,String>();
		//only handle maximize for now
		if(operation.equals(Operation.MAXIMIZE))
		{
			StatsRequestVO vo = new StatsRequestVO((String)input, (String)clientMetadata.get("USER_AGENT"), (String)clientMetadata.get("REFERRER"), 
					(String)clientMetadata.get("IP"),false);
			statQueue.add(vo);
		}
		else if(operation.equals(Operation.MINIMIZE))
		{
			if(params.get("URL_CREATED") != null && ((boolean)params.get("URL_CREATED")) == true)
			{
				StatsRequestVO vo = new StatsRequestVO((String)input,null,null,null,true);
				statQueue.add(vo);
			}
		}
		return input;
	}
}
