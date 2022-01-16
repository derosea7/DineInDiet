package com.dooragami.dineindiet.services;

import com.dooragami.dineindiet.models.Macro;

/**
 * Created by derosea7 on 8/26/2016.
 */
public class MacroService
{

    public MacroService()
    {

    }

    public MacroService(Macro macro)
    {

    }

    /// <summary>
    /// Checks if fat, carbs and protein is set for the Macro.
    /// </sumamry>
    public boolean isSet(Macro macro)
    {
        if (macro.getFat() >= 0
                && macro.getCarbohydrates() >= 0
                && macro.getProtein() >= 0)
        {
            return true;
        }
        return false;
    }

}
