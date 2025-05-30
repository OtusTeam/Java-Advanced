package com.example.javaconcurrent.livelock;

public class LiveLock {
    static class Spoon {
        private Dinner owner;
        public Spoon(Dinner d) { owner = d; }
        public Dinner getOwner() { return owner; }
        public synchronized void setOwner(Dinner d) { owner = d; }
        public synchronized void use() {
            System.out.printf("%s has eaten!", owner.name);
        }
    }

    static class Dinner {
        private String name;
        private boolean isHungry;

        public Dinner(String n) { name = n; isHungry = true; }
        public String getName() { return name; }
        public boolean isHungry() { return isHungry; }

        public void eatWith(Spoon spoon, Dinner spouse) {
            while (isHungry) {
                // Don't have the spoon, so wait patiently for spouse.
                if (spoon.owner != this) {
                    try { Thread.sleep(1); }
                    catch(InterruptedException e) { continue; }
                    continue;
                }

                // If spouse is hungry, insist upon passing the spoon.
                if (spouse.isHungry()) {
//                if (spouse.isHungry() && Math.random() < 0.5) {
                    System.out.printf(
                            "%s: You eat first my darling %s!%n",
                            name, spouse.getName());
                    spoon.setOwner(spouse);
                    continue;
                }

                // Spouse wasn't hungry, so finally eat
                spoon.use();
                isHungry = false;
                System.out.printf(
                        "%s: I am stuffed, my darling %s!%n",
                        name, spouse.getName());
                spoon.setOwner(spouse);
            }
        }
    }

    public static void main(String[] args) {
        final Dinner husband = new Dinner("Bob");
        final Dinner wife = new Dinner("Alice");

        final Spoon s = new Spoon(husband);

        new Thread(new Runnable() {
            public void run() { husband.eatWith(s, wife); }
        }).start();

        new Thread(new Runnable() {
            public void run() { wife.eatWith(s, husband); }
        }).start();
    }
}
