package com.aero.util;

import org.wikipedia.Wiki;

public class WikiManager
{
    private static Wiki _wiki;

    public static Wiki getWiki()
    {
        if(_wiki == null)
            _wiki = new Wiki("commons.wikimedia.org");

        return _wiki;
    }
}
