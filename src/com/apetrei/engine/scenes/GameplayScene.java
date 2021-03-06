package com.apetrei.engine.scenes;

import com.apetrei.engine.objects.ObjectBuilder;
import com.apetrei.engine.providers.ConfigHandler;
import com.apetrei.engine.gui.DialogLine;
import com.apetrei.engine.GameContainer;
import com.apetrei.engine.event.GlobalEvent;
import com.apetrei.engine.gui.UIElements.Button;
import com.apetrei.engine.input.InputType;
import com.apetrei.engine.sound.SoundManager;
import com.apetrei.engine.providers.ResourceLoader;
import com.apetrei.misc.Vector2;
import com.apetrei.misc.exceptions.ResourceNotFoundException;

import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;


public abstract class GameplayScene implements Scene {
    private boolean paused = false;
    private BufferedImage pauseMenuBackground;

    protected Set<GlobalEvent> hasHappened= new TreeSet<GlobalEvent>();
    protected GameContainer gameContainer;
    protected ObjectBuilder ob;

    protected String line;
    protected float timePassed =0;
    private float timeOfLastChange = 0;
    private float windChangeInterval = ConfigHandler.getWindChangeInterval();

    Random random = new Random();

    public GameplayScene(GameContainer gameContainer) {
        this.gameContainer = gameContainer;

        try {
            pauseMenuBackground = ResourceLoader.getInstance().getSprite("Pause_menu_background.png");
        } catch (ResourceNotFoundException e) {
            e.printStackTrace();
        }
        ob = new ObjectBuilder( gameContainer);

    }

    @Override
    public void init() {
        gameContainer.getMenuManager().clearUI();
        gameContainer.getPhysicsSystem().resetPhysicsSystem();
        gameContainer.getObjectManager().resetObjectManager();

        initializePauseMenu(gameContainer);
        gameContainer.getHudManager().getDialogManager().resetDialogueQueue();
        SoundManager.getInstance().stopAllSound();
        //RANDOM WIND CHANGES
        SoundManager.getInstance().playSound("engine.wav",true);
    }

    @Override
    public void update( float frameTime) {
        timePassed +=frameTime;

        if (gameContainer.getInput().isKey(KeyEvent.VK_ESCAPE, InputType.DOWN)) {
            paused = !paused;
            if( ConfigHandler.isDebugMode()) {
                if (paused) {
                    System.out.println("The game has been paused.");
                } else {
                    System.out.println("The game has been unpaused.");
                }
            }
        }

        //PAUSE MENU AND MAIN LOOP
        if (!paused) {
            //PHYSICS UPDATE
            gameContainer.getPhysicsSystem().updatePhysics(frameTime);
            //UPDATE
            gameContainer.getObjectManager().updateObjects(frameTime);
            //HUD UPDATE
            gameContainer.getHudManager().updateHUD(frameTime);
        }else {
            gameContainer.getMenuManager().update();
        }

        //RANDOM WIND CHANGES
        if( timeOfLastChange + windChangeInterval  < timePassed  ){
            int randomNum = random.nextInt(30) ;
            float randomX =  random.nextFloat() * 2 - 1;
            float randomY =  random.nextFloat() * 2 - 1;
            gameContainer.getPhysicsSystem().getWindEffect().setWind(new Vector2(randomX, randomY),randomNum);
            timeOfLastChange = timePassed;

            if(ConfigHandler.isDebugMode()) System.out.println("Wind power became:" + randomNum);
        }

        //SCORE TRAKING
        if(gameContainer.getGlobalEventQueue().checkCurrentEvent() == GlobalEvent.ENEMY_DESTROYED){
            // System.out.println(ConfigHandler.getScore() + 1 );
            ConfigHandler.setScore( ConfigHandler.getScore() + 1 );
        }
    }

    @Override
    public void render() {
        if (!paused) {
            gameContainer.getObjectManager().renderObjects();
            gameContainer.getHudManager().renderHUD();
        }else {
            gameContainer.getRenderer().getLayerRenderer().drawStaticSprite(new Vector2( ConfigHandler.getWidth()/2,ConfigHandler.getHeight()/2), 0.6f, pauseMenuBackground);
            gameContainer.getMenuManager().draw();
        }
    }

    private void initializePauseMenu(GameContainer gameContainer){

        //MENU BUTTON
        Vector2 button2Poz = new Vector2(ConfigHandler.getWidth() / 2, ConfigHandler.getHeight() / 2 -100);
        Button button2 =  Button.makeButton("Continua",button2Poz, 0.3f, () -> {
            paused = false;
        });

        //MENU BUTTON
        Vector2 button1Poz = new Vector2(ConfigHandler.getWidth() / 2, ConfigHandler.getHeight() / 2 + 100);
        Button button1 = Button.makeButton("Meniu",button1Poz, 0.3f, () -> {
            gameContainer.goBack();
            SoundManager.getInstance().stopAllSound();

        });

        //SETTINGS BUTTON
        Vector2 button3Poz = new Vector2(ConfigHandler.getWidth() / 2, ConfigHandler.getHeight() / 2 );
        Button button3 = Button.makeButton("Setari",button3Poz, 0.3f, () -> {
            gameContainer.goTo(new SettingsScene(gameContainer));
            paused = false;
        });

        gameContainer.getMenuManager().addUIElement(button1);
        gameContainer.getMenuManager().addUIElement(button2);
        gameContainer.getMenuManager().addUIElement(button3);
    }

    protected void playDialogue(String line, String audioFile ,int character){
        gameContainer.getHudManager().getDialogManager().addDialogueLine(new DialogLine(
                line ,
                audioFile,
                SoundManager.getInstance().getLenghtOfClip(audioFile),
                character)
        );
    }
}
