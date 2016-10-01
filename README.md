infraviewer
===========

InfraView is a web-based tool focused on municipal asset and infrastructure management.

It is designed to be able to support a heirarchy of organizations, and each organization can support a range of "Asset Classes".
An Asset Class could be any type of depreciating assett that needs to be tracked and maintained. 

For example "Fire Hydrant" would be an Asset Class.

The Asset Manangemetn allows for an asset class to be evaluated in terms of its condition (say on a 1 to 10 scale).  An individual Asset, of an asset class, will decay over time, or depreciate, and investment will be needed to Install, Inspect, Maintain, Remove or Replace it as time goes on. 

The primary function of this applciation is to project the expecteed date and cost of these operations.


Application structure:

It is developed on Grails 2.3.4, the majority of the application dev in Groovy.



Current functionality as of 4/1/2014

1) Enterprise/Organization management and CRUD
2) AssetClass management and CRUD (introductory first cut for steel thread)
3) Asset CRUD
4) AssetStatusEvent Add/Delete; AJAX based page
5) Basic asset status projection (GASB 34 compliance is goal)

TODO:
Work Orders 
Sub contractor management
AssetClass Types expansions including improved configurability
Calendars
Mapping!

