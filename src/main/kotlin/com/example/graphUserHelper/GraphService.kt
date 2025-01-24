package com.example.graphUserHelper

import com.microsoft.graph.models.User
import com.microsoft.graph.serviceclient.GraphServiceClient
import com.microsoft.graph.users.item.UserItemRequestBuilder
import org.springframework.stereotype.Service
import java.util.*
import java.util.function.Consumer

@Service
class GraphService (private val graphServiceClient: GraphServiceClient){

    fun getUser(exoId: String){
        var user: User? = graphServiceClient.users().byUserId(exoId)
            .get(Consumer<UserItemRequestBuilder.GetRequestConfiguration> { requestConfiguration: UserItemRequestBuilder.GetRequestConfiguration ->
                Objects.requireNonNull(requestConfiguration.queryParameters)
                requestConfiguration.queryParameters.select = arrayOf<String>(
                    "id",
                    "userPrincipalName",
                    "displayName",
                    "onPremisesImmutableId",
                    "onPremisesExtensionAttributes",
                    "assignedLicenses"
                )
            })

        println(user)
    }
}