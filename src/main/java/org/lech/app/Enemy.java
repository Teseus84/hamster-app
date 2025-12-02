package org.lech.app;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

public class Enemy {

    // image that represents the enemy position on the board
    private BufferedImage image;
    // current position of the enemy on the board grid
    private Point pos;

    public Enemy() {
        // load the assets
        loadImage();

        Random rand = new Random();
        int startXPos = rand.nextInt(2, Board.COLUMNS - 1);
        int startYPos = rand.nextInt(2, Board.ROWS - 1);

        // initialize the state
        pos = new Point(startXPos, startYPos);

    }

    private void loadImage() {
        try {
            // you can use just the filename if the image file is in your
            // project folder, otherwise you need to provide the file path.
            InputStream in = getClass().getClassLoader().getResourceAsStream("images/enemy.png");
            image = ImageIO.read(in);
        } catch (IOException exc) {
            System.out.println("Error opening player image file: " + exc.getMessage());
        }
    }

    public void draw(Graphics g, ImageObserver observer) {
        // with the Point class, note that pos.getX() returns a double, but 
        // pos.x reliably returns an int. https://stackoverflow.com/a/30220114/4655368
        // this is also where we translate board grid position into a canvas pixel
        // position by multiplying by the tile size.
        g.drawImage(
                image,
                pos.x * Board.TILE_SIZE,
                pos.y * Board.TILE_SIZE,
                observer
        );
    }


    public void tick() {
        // this gets called once every tick, before the repainting process happens.
        // so we can do anything needed in here to update the state of the enemy.

        Random rand = new Random();
        int direction = rand.nextInt(4);

        if (direction == 0) {
            pos.translate(0, -1);
        }
        if (direction == 1) {
            pos.translate(0, 1);
        }
        if (direction == 2) {
            pos.translate(1, 0);
        }
        if (direction == 3) {
            pos.translate(-1, 0);
        }

        // prevent the enemy from moving off the edge of the board sideways
        if (pos.x < 0) {
            pos.x = 1;
        } else if (pos.x >= Board.COLUMNS) {
            pos.x = Board.COLUMNS - 2;
        }
        // prevent the enemy from moving off the edge of the board vertically
        if (pos.y < 0) {
            pos.y = 1;
        } else if (pos.y >= Board.ROWS) {
            pos.y = Board.ROWS - 2;
        }
    }


    public Point getPos() {
        return pos;
    }

}
