package fr.skyfight.fluidimage.utils;

public class Vector2D {

        private float x;
        private float y;

        public Vector2D(float x, float y) {
            this.x = x;
            this.y = y;
        }

        public Vector2D(Vector2D vector2D, Vector2D lastVector2D) {
            this.x = vector2D.getX() - lastVector2D.getX();
            this.y = vector2D.getY() - lastVector2D.getY();
        }

        public Vector2D() {
            this.x = 0;
            this.y = 0;
        }

        public void add(Vector2D vector2D) {
            this.x += vector2D.getX();
            this.y += vector2D.getY();
        }

        public void remove(Vector2D vector2D) {
            this.x -= vector2D.getX();
            this.y -= vector2D.getY();
        }

        public void multiply(float value) {
            this.x *= value;
            this.y *= value;
        }

        public void divide(float value) {
            this.x /= value;
            this.y /= value;
        }

        public void add(float value) {
            this.x += value;
            this.y += value;
        }

        public void remove(float value) {
            this.x -= value;
            this.y -= value;
        }

        public void addX(float x) {
            this.x += x;
        }

        public void addY(float y) {
            this.y += y;
        }

        public void removeX(float x) {
            this.x -= x;
        }

        public void removeY(float y) {
            this.y -= y;
        }

        public void add(float x, float y) {
            this.x += x;
            this.y += y;
        }

        public void remove(float x, float y) {
            this.x -= x;
            this.y -= y;
        }

        public void reset() {
            this.x = 0;
            this.y = 0;
        }

        public float getX() {
            return x;
        }

        public void setX(float x) {
            this.x = x;
        }

        public float getY() {
            return y;
        }

        public void setY(float y) {
            this.y = y;
        }

        public void set(float x, float y) {
            this.x = x;
            this.y = y;
        }

        public void set(Vector2D vector2D) {
            this.x = vector2D.getX();
            this.y = vector2D.getY();
        }

        public void set(Location location, Location lastLocation) {
            this.x = location.getX() - lastLocation.getX();
            this.y = location.getY() - lastLocation.getY();
        }

        public void set(Location location) {
            this.x = location.getX();
            this.y = location.getY();
        }

        public float getMagnitude() {
            return (float) Math.sqrt(x * x + y * y);
        }

        public void normalize() {
            float magnitude = getMagnitude();
            x /= magnitude;
            y /= magnitude;
        }
}
