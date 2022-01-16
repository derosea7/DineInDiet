package com.dooragami.dineindiet.models;

/**
 * Created by derosea7 on 8/26/2016.
 */
public class Body
{
    double mWeight;

    // Some numeric representation of the body's general digestion speed.
    // Could be a percentage of some max speed, where speed can probably be described by
    // a quadratic function correlating to age.
    byte mDigestionSpeed;

    // 1-100.. since this can only be an estimate, precision isn't important.
    byte mPercentWater;

    // Body has a service called digestion.. in terms of programming, we'll call the service, Digestion.

}
