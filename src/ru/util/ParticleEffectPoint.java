package ru.util;

import de.slikey.effectlib.Effect;
import de.slikey.effectlib.EffectType;
import de.slikey.effectlib.util.ParticleEffect;
import ru.main.HardcorePlugin;

public class ParticleEffectPoint extends Effect {

	public ParticleEffectPoint() {
		super(HardcorePlugin.instance.em);
		type = EffectType.INSTANT;
	}

	public ParticleEffect particle = ParticleEffect.REDSTONE;
	public int amount = 1;

	@Override
	public void onRun() {
		particle.display(particle.getData(material, materialData), this.getLocation(), color, visibleRange, particleOffsetX, particleOffsetY, particleOffsetZ,
				speed, amount);
	}

}
