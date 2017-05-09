# HidePlayersService
This service simply remembers whether a player wants to see other players in the lobby.

## Endpoints

### /players/{uuid} [GET]:
#### Gets visibility state of player

**Response**:
```json
{"state": "ALL"}
```

### /players/{uuid} [PUT]:
#### Sets visibility state of player
States: NONE, PLAYERS_10, PLAYERS_25, PLAYERS_50, PLAYERS_100, ALL


**Request**:
```json
{
"state": "NONE"
}
```

**Response**:
```json
{
"success": true
}
```
