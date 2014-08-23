package com.mechzombie.infraview

import org.apache.commons.lang.builder.HashCodeBuilder

class InfraUserRole implements Serializable {

	InfraUser user
	Role role

	boolean equals(other) {
		if (!(other instanceof InfraUserRole)) {
			return false
		}

		other.user?.id == user?.id &&
			other.role?.id == role?.id
	}

	int hashCode() {
		def builder = new HashCodeBuilder()
		if (user) builder.append(user.id)
		if (role) builder.append(role.id)
		builder.toHashCode()
	}

	static InfraUserRole get(long userId, long roleId) {
		find 'from InfraUserRole where user.id=:userId and role.id=:roleId',
			[userId: userId, roleId: roleId]
	}

	static InfraUserRole create(InfraUser user, Role role, boolean flush = false) {
		new InfraUserRole(user: user, role: role).save(flush: flush, insert: true)
	}

	static boolean remove(InfraUser user, Role role, boolean flush = false) {
		InfraUserRole instance = InfraUserRole.findByUserAndRole(user, role)
		if (!instance) {
			return false
		}

		instance.delete(flush: flush)
		true
	}

	static void removeAll(InfraUser user) {
		executeUpdate 'DELETE FROM InfraUserRole WHERE user=:user', [user: user]
	}

	static void removeAll(Role role) {
		executeUpdate 'DELETE FROM InfraUserRole WHERE role=:role', [role: role]
	}

	static mapping = {
		id composite: ['role', 'user']
		version false
	}
}
