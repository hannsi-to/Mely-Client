package me.hannsi.melyclient.util.render.render2D.particle;

import me.hannsi.melyclient.util.render.nanovg.render.NanoVGRenderUtil;
import me.hannsi.melyclient.util.system.math.MathUtil;
import me.hannsi.melyclient.util.system.math.color.ColorUtil;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ParticleSystem {
    private static final float SPEED = 0.1f;
    private final List<Particle> particleList = new ArrayList<>();
    private final boolean mouse;
    private final int dist;
    private final float lineWidth;
    private Color color;

    public ParticleSystem(int displayWidth, int displayHeight, int initAmount, boolean mouse, int dist, float lineWidth, Color color) {
        addParticles(initAmount, displayWidth, displayHeight);
        this.mouse = mouse;
        this.dist = dist;
        this.color = color;
        this.lineWidth = lineWidth;
    }

    public void addParticles(int amount, int displayWidth, int displayHeight) {
        for (int i = 0; i < amount; i++) {
            particleList.add(Particle.generateParticle(displayWidth, displayHeight));
        }
    }


    public void tick(int delta, int displayWidth, int displayHeight) {
        for (Particle particle : particleList) {
            particle.tick(delta, SPEED, displayWidth, displayHeight);
        }
    }

    public void render(float mouseX, float mouseY, int displayWidth, int displayHeight) {
        for (Particle particle : particleList) {
            NanoVGRenderUtil.drawPoint(particle.getX(), particle.getY(), particle.getSize() / 5, color);

            if (mouse) {
                float distance = (float) MathUtil.distance(particle.getX(), particle.getY(), mouseX, mouseY);
                if (distance < dist) {
                    NanoVGRenderUtil.drawLine(particle.getX(), particle.getY(), mouseX, mouseY, lineWidth, color);
                }

            } else {

                float nearestDistance = 0;
                Particle nearestParticle = null;

                for (Particle particle1 : particleList) {
                    float distance = particle.getDistanceTo(particle1);
                    if (distance <= dist && (MathUtil.distance(mouseX, mouseY, particle.getX(), particle.getY()) <= dist || MathUtil.distance(mouseX, mouseY, particle1.getX(), particle1.getY()) <= dist) && (nearestDistance <= 0 || distance <= nearestDistance)) {

                        nearestDistance = distance;
                        nearestParticle = particle1;

                    }
                }

                if (nearestParticle != null) {
                    float alpha = Math.min(1.0f, Math.min(1.0f, 1.0f - nearestDistance / dist));
                    color = ColorUtil.setAlpha(color, alpha);
                    NanoVGRenderUtil.drawLine(particle.getX(), particle.getY(), nearestParticle.getX(), nearestParticle.getY(), lineWidth, color);
                }
            }
        }
    }
}
