
package com.mechzombie.infraview

/**
 *
 * @author David
 */
enum AssetStatusEventType {
    Installation(false), Maintenance(true), Inspection(true), Removal(false)
	
	AssetStatusEventType(repeatingEvent) {
		isRepeating = repeatingEvent
	}
	private final isRepeating
	
	def isRepeating() {
		isRepeating
	}
}

