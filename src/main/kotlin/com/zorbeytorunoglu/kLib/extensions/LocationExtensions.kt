package com.zorbeytorunoglu.kLib.extensions

import com.zorbeytorunoglu.kLib.MCPlugin
import com.zorbeytorunoglu.kLib.task.MCDispatcher
import com.zorbeytorunoglu.kLib.task.Scopes
import com.zorbeytorunoglu.kLib.task.suspendFunctionSync
import com.zorbeytorunoglu.kLib.task.suspendFunctionSyncWithResult
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.bukkit.Bukkit
import org.bukkit.Effect
import org.bukkit.Location
import org.bukkit.entity.EntityType
import org.bukkit.inventory.ItemStack

/**
 * Spawns an entity in the location.
 * @param entityType EntityType
 */
fun Location.spawnEntity(entityType: EntityType) {
    this.world?.spawnEntity(this, entityType)
}

/**
 * Drops the given item stack to the location.
 * @param item ItemStack
 */
fun Location.dropItem(item: ItemStack) {
    world?.dropItem(this, item)
}

/**
 * Drops an item stack naturally.
 * @param item ItemStack
 */
fun Location.dropItemNaturally(item: ItemStack) {
    world?.dropItemNaturally(this, item)
}

/**
 * Strikes a lightning on the location.
 */
fun Location.strikeLightning() {
    world?.strikeLightning(this)
}

/**
 * Sends a lightning strike effect to the location.
 */
fun Location.strikeLightningEffect() {
    world?.strikeLightningEffect(this)
}

/**
 * Creates an explosion with the given power in the location.
 * @param power Power of the explosion
 */
fun Location.createExplosion(power: Float): Boolean {
    return world?.createExplosion(this, power) ?: false
}

/**
 * Creates an explosion with the given power and an option to enable or disable the fire
 * that will be caused by the explosion.
 * @param power Power float
 * @param fire Should cause fire
 */
fun Location.createExplosion(power: Float, fire: Boolean): Boolean {
    return world?.createExplosion(this, power, fire) ?: false
}

/**
 * Creates an explosion with the given power and the option to enable or disable the fire
 * that will be caused by the explosion and option to enable or disable the block break.
 * @param power Power float
 * @param fire Should cause fire boolean
 * @param breakBlocks Should break blocks boolean
 */
fun Location.createExplosion(power: Float, fire: Boolean, breakBlocks: Boolean): Boolean {
    return world?.createExplosion(this.x, this.y, this.z, power, fire, breakBlocks) ?: false
}

/**
 * Creates an explosion without causing halt in server with the given power and an option to
 * enable or disable the fire that will be caused by the explosion and block break.
 * @param plugin MCPlugin instance
 * @param power Power float
 * @param fire Should cause fire boolean
 * @param breakBlocks Should break blocks boolean
 */
suspend fun Location.createExplosionAsync(plugin: MCPlugin, power: Float, fire: Boolean, breakBlocks: Boolean): Boolean {
    return Scopes.supervisorScope.async {
        plugin.suspendFunctionSyncWithResult {
            world?.createExplosion(this@createExplosionAsync.x, this@createExplosionAsync.y, this@createExplosionAsync.z, power, fire, breakBlocks) ?: false
        }
    }.await()
}

/**
 * Plays an effect in the location.
 * @param effect Effect
 * @param data Effect data
 */
fun Location.playEffect(effect: Effect, data: Int) {
    world?.playEffect(this, effect, data)
}

/**
 * Plays an effect in the location.
 * @param effect Effect
 * @param data Effect data
 * @param radius Radius
 */
fun Location.playEffect(effect: Effect, data: Int, radius: Int) {
    world?.playEffect(this, effect, data, radius)
}

/**
 * Plays an effect in the location.
 * @param effect Effect
 * @param data Any
 */
fun <T> Location.playEffect(effect: Effect, data: T) {
    world?.playEffect(this, effect, data)
}

/**
 * Plays an effect in the location.
 * @param effect Effect
 * @param data Effect data
 * @param radius Radius
 */
fun <T> Location.playEffect(effect: Effect, data: T, radius: Int) {
    world?.playEffect(this, effect, data, radius)
}

/**
 * Spawns entities without causing halt.
 * @param mcPlugin MCPlugin instance
 * @param entityType EntityType
 */
suspend fun Location.spawnEntityAsync(mcPlugin: MCPlugin, entityType: EntityType) {

    Scopes.supervisorScope.launch(MCDispatcher(mcPlugin, async = false)) {
        mcPlugin.suspendFunctionSync { world?.spawnEntity(this@spawnEntityAsync, entityType) }
    }.join()

}

/**
 * Gives the location as a legible String.
 * @return String
 */
fun Location.toLegibleString(): String =
    "${this.world?.name};${this.x};${this.y};${this.z};${this.yaw};${this.pitch}"

/**
 * Converts a legible Location String to Location.
 * @return Location
 */
fun Location.fromLegibleString(string: String): Location {

    val args: List<String> = string.split(";")

    return Location(Bukkit.getWorld(args[0]),args[1].toDouble(),args[2].toDouble(),args[3].toDouble(),
        args[4].toFloat(), args[5].toFloat())

}