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
package org.da4.urlminimizer;

import java.util.HashMap;
import java.util.Map;

public enum Operation {

	MINIMIZE("Minimize"), MAXIMIZE("Maximize");
	private String strOperation;
	private static Map<String, Operation> operationMap = new HashMap<String, Operation>();
	static {
		for (Operation operation : Operation.values()) {
			operationMap.put(operation.strOperation, operation);
		}
	}

	private Operation(String operation) {
		this.strOperation = operation;
	}

	public static Operation get(String operation) {
		return operationMap.get(operation);
	}
}
