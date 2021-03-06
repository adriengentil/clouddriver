/*
 * Copyright 2016 Google, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.netflix.spinnaker.clouddriver.appengine.deploy.validators

import com.netflix.spinnaker.clouddriver.appengine.AppengineOperation
import com.netflix.spinnaker.clouddriver.appengine.deploy.description.DestroyAppengineDescription
import com.netflix.spinnaker.clouddriver.appengine.security.AppengineNamedAccountCredentials
import com.netflix.spinnaker.clouddriver.deploy.DescriptionValidator
import com.netflix.spinnaker.clouddriver.deploy.ValidationErrors
import com.netflix.spinnaker.clouddriver.orchestration.AtomicOperations
import com.netflix.spinnaker.credentials.CredentialsRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@AppengineOperation(AtomicOperations.DESTROY_SERVER_GROUP)
@Component("destroyAppengineDescriptionValidator")
class DestroyAppengineDescriptionValidator extends DescriptionValidator<DestroyAppengineDescription> {
  @Autowired
  CredentialsRepository<AppengineNamedAccountCredentials> credentialsRepository

  @Override
  void validate(List priorDescriptions, DestroyAppengineDescription description, ValidationErrors errors) {
    def helper = new StandardAppengineAttributeValidator("destroyAppengineAtomicOperationDescription", errors)

    helper.validateCredentials(description.accountName, credentialsRepository)
    helper.validateNotEmpty(description.serverGroupName, "serverGroupName")
  }
}
