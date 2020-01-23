package org.frc.team5409.churro.control;

public abstract interface GlobalProvider<T, V extends GlobalInstance<T>> {
	public V getGlobal();
}