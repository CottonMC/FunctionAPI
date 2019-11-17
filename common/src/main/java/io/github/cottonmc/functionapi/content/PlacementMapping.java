package io.github.cottonmc.functionapi.content;

import io.github.cottonmc.functionapi.api.content.enums.PlacementDirection;
import io.github.cottonmc.functionapi.api.content.enums.PlacementPosition;



public class PlacementMapping {

    private String stateName;
    private String stateValue;
    private PlacementDirection placementDirection;
    private PlacementPosition placementPosition;
    private PlacementDirection facingDirection;


    public PlacementMapping(String stateName, String stateValue, PlacementDirection placementDirection, PlacementPosition placementPosition, PlacementDirection facingDirection) {
        this.stateName = stateName;
        this.stateValue = stateValue;
        this.placementDirection = placementDirection;
        this.placementPosition = placementPosition;
        this.facingDirection = facingDirection;
    }

    public String getStateValue() {
        return stateValue;
    }

    public String getStateName() {
        return stateName;
    }

    public PlacementDirection getPlacementDirection() {
        return placementDirection;
    }

    public PlacementMapping setPlacementDirection(PlacementDirection placementDirection) {
        this.placementDirection = placementDirection;
        return this;
    }

    public PlacementPosition getPlacementPosition() {
        return placementPosition;
    }


    public PlacementDirection getFacingDirection() {
        return facingDirection;
    }
}
