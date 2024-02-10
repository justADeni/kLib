package com.zorbeytorunoglu.kLib.extensions

import com.zorbeytorunoglu.kLib.MCPlugin
import com.zorbeytorunoglu.kLib.cuboid.Cuboid
import com.zorbeytorunoglu.kLib.task.MCDispatcher
import com.zorbeytorunoglu.kLib.task.Scopes
import kotlinx.coroutines.launch
import org.bukkit.*
import org.bukkit.block.Block
import org.bukkit.command.CommandSender
import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

/**
 * Checks if the player is in the cuboid region that is formed by the two given locations.
 * @param playerLocation Location of the player.
 * @param location1 First location
 * @param location2 Second location
 * @return True if the player is in the cuboid, false if not.
 */
fun Player.isInside(playerLocation: Location, location1: Location, location2: Location): Boolean {

    val cuboid = Cuboid(location1, location2)

    return cuboid.isIn(playerLocation)

}

/**
 * Checks if the player's inventory is full.
 * @return Boolean: True if there is no empty slot, false if there is.
 */
fun Player.isInventoryFull(): Boolean {
    return this.inventory.firstEmpty() == -1;
}

/**
 * Clears all the armor player wears.
 */
fun Player.clearArmor() {
    player?.inventory?.armorContents = arrayOf<ItemStack?>(null, null, null, null)
}

/**
 * Clears both all the armors and inventory together.
 */
fun Player.clearAllInventory() {
    inventory.clear()
    clearArmor()
}

/**
 * Gets the item in hand if it is not null and the type is not AIR.
 * @return ItemStack
 */
val Player.hasItemInHand get() = inventory.itemInMainHand.type != Material.AIR

/**
 * Plays sound in player's location.
 * @param sound Sound enum
 * @param volume Volume float
 * @param pitch Pitch float
 */
fun Player.playSound(sound: Sound, volume: Float, pitch: Float) = playSound(location, sound, volume, pitch)

/**
 * Plays an effect in player's location.
 * @param effect Effect
 * @param data Effect data
 */
fun <T> Player.playEffect(effect: Effect, data: T? = null) = player?.location?.let { playEffect(it, effect, data) }

/**
 * Resets the walk speed of the player.
 */
fun Player.resetWalkSpeed() {
    player?.walkSpeed = 0.2f
}

/**
 * Resets the fly speed of the player.
 */
fun Player.resetFlySpeed() {
    player?.flySpeed = 0.1f
}

/**
 * Teleports the player to the location without causing halt.
 * It actually teleports them synchronized since you can not access the Bukkit API
 * async, yet it is named this way since all the Spigot developers are used to this term due to other libraries/APIs.
 * @param mcPlugin MCPlugin
 * @param location Location
 */
suspend fun Player.teleportAsync(mcPlugin: MCPlugin, location: Location) {
    Scopes.supervisorScope.launch(MCDispatcher(mcPlugin, async = false)) {

        this@teleportAsync.teleport(location)

    }.join()
}

/**
 * Teleports the player to the other player without causing halt.
 * It actually teleports them synchronized since you can not access the Bukkit API
 * async, yet it is named this way since all the Spigot developers are used to this term due to other libraries/APIs.
 * @param mcPlugin MCPlugin
 * @param targetPlayer Player
 */
suspend fun Player.teleportAsync(mcPlugin: MCPlugin, targetPlayer: Player) {
    Scopes.supervisorScope.launch(MCDispatcher(mcPlugin, async = false)) {

        this@teleportAsync.teleport(targetPlayer.location)

    }.join()
}

/**
 * Teleports the entity to the location without causing halt.
 * It actually teleports them synchronized since you can not access the Bukkit API
 * async, yet it is named this way since all the Spigot developers are used to this term due to other libraries/APIs.
 * @param mcPlugin MCPlugin
 * @param location Location
 */
suspend fun Entity.teleportAsync(mcPlugin: MCPlugin, location: Location) {
    Scopes.supervisorScope.launch(MCDispatcher(mcPlugin, async = false)) {

        this@teleportAsync.teleport(location)

    }.join()
}

/**
 * Teleports the entity to the other entity without causing halt.
 * It actually teleports them synchronized since you can not access the Bukkit API
 * async, yet it is named this way since all the Spigot developers are used to this term due to other libraries/APIs.
 * @param mcPlugin MCPlugin
 * @param entity Entity
 */
suspend fun Entity.teleportAsync(mcPlugin: MCPlugin, entity: Entity) {

    Scopes.supervisorScope.launch(MCDispatcher(mcPlugin, async = false)) {

        this@teleportAsync.teleport(entity.location)

    }.join()

}

/**
 * Heals and feeds the player.
 */
fun Player.heal() {
    this.health = 20.0
    this.foodLevel = 20
}

/**
 * Feeds the player.
 */
fun Player.feed() {
    this.foodLevel = 20
}

/**
 * Sends a "String Collection" to the player as a message.
 * @param messageList String Collection
 */
fun Player.sendMessage(messageList: Collection<String>) = messageList.forEach { this.sendMessage(it) }

/**
 * Sends a "String Collection" to the command sender as a message.
 * @param messageList String Collection
 */
fun CommandSender.sendMessage(messageList: Collection<String>) = messageList.forEach { this.sendMessage(it) }

/**
 * Gets the block below the player.
 * @return Block
 */
fun Player.getBlockBelow(): Block = this.location.subtract(0.0, 1.0, 0.0).block

/**
 * Kills the player.
 */
fun Player.kill() {
    player?.health = 0.0
}

fun Player.ejectPassengers() {
    if (player?.vehicle != null) player?.vehicle?.eject();
    player?.eject();
}