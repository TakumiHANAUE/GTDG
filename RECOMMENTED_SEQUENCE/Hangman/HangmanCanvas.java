
/*
 * File: HangmanCanvas.java
 * ------------------------
 * This file keeps track of the Hangman display.
 */
//package Hangman;

//import acm.graphics.*;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Canvas;
import java.awt.Color;

public class HangmanCanvas extends Canvas {
    private int hangmanStep;
    private String guessedWord;
    private StringBuffer stringOfIncorrectChars;

    public void init()
    {
        // this.setSize(IMAGE_WIDTH, IMAGE_HEIGHT);
        this.hangmanStep = 0;
        this.guessedWord = new String();
        this.stringOfIncorrectChars = new StringBuffer();
        this.setBackground(Color.WHITE);
    }

    /** Resets the display so that only the scaffold appears */
    public void reset() {
        this.init();
        this.repaint();
    }

    public void paint(Graphics g)
    {
        this.drawHangman(g);
    }

    private void drawHangman(Graphics g)
    {
        // scaffold and rope
        g.drawLine(scaffold_top.x, scaffold_top.y, scaffold_top.x, scaffold_top.y + SCAFFOLD_HEIGHT);
        g.drawLine(scaffold_top.x, scaffold_top.y, scaffold_top.x + BEAM_LENGTH, scaffold_top.y);
        g.drawLine(rope_top.x, rope_top.y, rope_end.x, rope_end.y);
        g.drawString("The Word", 70, 420);
        g.drawString(this.guessedWord, 80, 435);
        g.drawString("Incorrect Guesses", 220, 420);
        g.drawString(this.stringOfIncorrectChars.toString(), 230, 435);
        if (this.hangmanStep >= 1)
        {
            // Head
            g.drawOval(head_org.x, head_org.y, HEAD_RADIUS * 2, HEAD_RADIUS * 2);
        }
        if (this.hangmanStep >= 2)
        {
            // Boby
            g.drawLine(body_top.x, body_top.y, body_end.x, body_end.y);
        }
        if (this.hangmanStep >= 3)
        {
            // Left Arm
            g.drawLine(arm_org.x, arm_org.y, left_elbow.x, left_elbow.y);
            g.drawLine(left_elbow.x, left_elbow.y, left_elbow.x, left_elbow.y + LOWER_ARM_LENGTH);
        }
        if (this.hangmanStep >= 4)
        {
            // Right Arm
            g.drawLine(arm_org.x, arm_org.y, right_elbow.x, right_elbow.y);
            g.drawLine(right_elbow.x, right_elbow.y, right_elbow.x, right_elbow.y + LOWER_ARM_LENGTH);
        }
        if (this.hangmanStep >= 5)
        {
            // Left Leg
            g.drawLine(body_end.x, body_end.y, left_hip.x, left_hip.y);
            g.drawLine(left_hip.x, left_hip.y, left_ankle.x, left_ankle.y);
        }
        if (this.hangmanStep >= 6)
        {
            // Right Leg
            g.drawLine(body_end.x, body_end.y, right_hip.x, right_hip.y);
            g.drawLine(right_hip.x, right_hip.y, right_ankle.x, right_ankle.y);
        }
        if (this.hangmanStep >= 7)
        {
            // Left Foot
            g.drawLine(left_ankle.x, left_ankle.y, left_ankle.x - FOOT_LENGTH, left_ankle.y);
        }
        if (this.hangmanStep >= 8)
        {
            // Right Foot
            g.drawLine(right_ankle.x, right_ankle.y, right_ankle.x + FOOT_LENGTH, right_ankle.y);
        }
    }

    public void nextHangmanStep()
    {
        this.hangmanStep++;
        this.repaint();
    }

    /**
     * Updates the word on the screen to correspond to the current state of the
     * game. The argument string shows what letters have been guessed so far;
     * unguessed letters are indicated by hyphens.
     */
    public void displayWord(String word) {
        this.guessedWord = new String(word);
        this.repaint();
    }

    /**
     * Updates the display to correspond to an incorrect guess by the user. Calling
     * this method causes the next body part to appear on the scaffold and adds the
     * letter to the list of incorrect guesses that appears at the bottom of the
     * window.
     */
    public void noteIncorrectGuess(char letter) {
        this.stringOfIncorrectChars.append(letter);
        this.repaint();
    }

    // Hangman points
    private Point scaffold_top = new Point(50, 50);
    private Point rope_top = new Point(scaffold_top.x + BEAM_LENGTH, scaffold_top.y);
    private Point rope_end = new Point(rope_top.x, rope_top.y + ROPE_LENGTH);
    private Point head_org = new Point(rope_end.x - HEAD_RADIUS, rope_end.y);
    private Point body_top = new Point(rope_end.x, rope_end.y + HEAD_RADIUS * 2);
    private Point body_end = new Point(body_top.x, body_top.y + BODY_LENGTH);
    private Point arm_org = new Point(body_top.x, body_top.y + ARM_OFFSET_FROM_HEAD);
    private Point left_elbow = new Point(arm_org.x - UPPER_ARM_LENGTH, arm_org.y);
    private Point right_elbow = new Point(arm_org.x + UPPER_ARM_LENGTH, arm_org.y);
    private Point left_hip = new Point(body_end.x - HIP_WIDTH, body_end.y);
    private Point left_ankle = new Point(left_hip.x, left_hip.y + LEG_LENGTH);
    private Point right_hip = new Point(body_end.x + HIP_WIDTH, body_end.y);
    private Point right_ankle = new Point(right_hip.x, right_hip.y + LEG_LENGTH);

    /* Constants for the simple version of the picture (in pixels) */
    private static final int SCAFFOLD_HEIGHT = 360;
    private static final int BEAM_LENGTH = 144;
    private static final int ROPE_LENGTH = 18;
    private static final int HEAD_RADIUS = 36;
    private static final int BODY_LENGTH = 144;
    private static final int ARM_OFFSET_FROM_HEAD = 28;
    private static final int UPPER_ARM_LENGTH = 72;
    private static final int LOWER_ARM_LENGTH = 44;
    private static final int HIP_WIDTH = 36;
    private static final int LEG_LENGTH = 108;
    private static final int FOOT_LENGTH = 28;

}