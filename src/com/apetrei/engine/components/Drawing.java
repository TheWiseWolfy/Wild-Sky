package com.apetrei.engine.components;

import com.apetrei.engine.Component;
import com.apetrei.engine.GameContainer;

public class Drawing extends Component {

    public Drawing(GameContainer gameContainer ) {
        super(gameContainer);
    }

    @Override
    public void componentUpdate(GameContainer gameContainer) {
       if(gameContainer.getInput().isMouseKeyPressed(1)) {
           gameContainer.getRenderer().setPixel((int) (gameContainer.getInput().getMouseX()), (int) (gameContainer.getInput().getMouseY()));}
    }

    @Override
    public void componentRender(GameContainer gameContainer ) {

    }
}
