package com.gcolella.shootertest;

public interface DamageObject {
abstract float getDamage();
abstract boolean penetration();
abstract int getCreatorID();
abstract boolean targetable(UniverseObject obj);
abstract int getExplosionRadius();
}
