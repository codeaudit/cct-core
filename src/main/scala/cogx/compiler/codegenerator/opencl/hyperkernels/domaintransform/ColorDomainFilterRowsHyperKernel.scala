/*
 * (c) Copyright 2016 Hewlett Packard Enterprise Development LP
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cogx.compiler.codegenerator.opencl.hyperkernels.domaintransform

import cogx.compiler.codegenerator.opencl.fragments._
import cogx.platform.types.{VirtualFieldRegister, FieldType}
import cogx.compiler.codegenerator.common.FieldPolicies._
import cogx.compiler.parser.op.NaryOpcode

/** Factory for creating hyper kernels that implement domain transform filtering
  * on rows of an image.
  *
  * @author Greg Snider
  */
private[cogx]
object ColorDomainFilterRowsHyperKernel {

  /** Create a hyperkernel that implements the domain transform for the rows
    * of an input color field.
    *
    * @param in The input virtual field register driving this kernel.
    * @param operation The binary opcode for this operation.
    * @param resultType The FieldType of the result of this kernel.
    * @return Synthesized hyperkernel for the operation.
    */
  def apply(in: Array[VirtualFieldRegister],
            operation: NaryOpcode,
            resultType: FieldType): HyperKernel =
  {
    require(in.length == 3)
    require(in(0).fieldType.dimensions == 2)
    val expectedResultType = in(0).fieldType
    require(resultType == expectedResultType)
    val addressing = SmallTensorAddressing
    require(isColorField(in(0).fieldType))
    new ColorDomainFilterHyperKernel(in, operation, resultType, addressing, true)
  }
}

