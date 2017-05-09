/*
 * Copyright 2017 Exorath
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.exorath.service.hideplayers.service;

import com.exorath.service.hideplayers.Service;
import com.exorath.service.hideplayers.res.Success;
import com.exorath.service.hideplayers.res.VisibilityPlayer;
import com.exorath.service.hideplayers.res.VisibleState;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.UpdateOptions;
import org.bson.Document;

/**
 * Created by toonsev on 5/9/2017.
 */
public class MongoService implements Service {
    private MongoCollection<Document> visibilityPlayersCollection;

    public MongoService(MongoClient mongoClient, String databaseName) {
        this.visibilityPlayersCollection = mongoClient.getDatabase(databaseName).getCollection("visibilityPlayers");
    }

    @Override
    public VisibilityPlayer getVisibilityPlayer(String uuid) {
        Document doc = visibilityPlayersCollection.find(new Document("_id", uuid)).first();
        return doc == null ? new VisibilityPlayer(VisibleState.ALL) : new VisibilityPlayer(VisibleState.valueOf(doc.getString("state")));
    }

    @Override
    public Success setVisibilityPlayer(String uuid, VisibilityPlayer player) {
        try {
            visibilityPlayersCollection.updateOne(new Document("_id", uuid), new Document("state", player.getState()), new UpdateOptions().upsert(true));
            return new Success(true);
        } catch (Exception e) {
            e.printStackTrace();
            return new Success(false, -1, e.getMessage());
        }
    }
}
