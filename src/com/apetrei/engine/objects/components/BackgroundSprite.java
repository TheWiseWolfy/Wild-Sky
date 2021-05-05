package com.apetrei.engine.objects.components;

import com.apetrei.misc.exceptions.SpriteNotFoundException;
import com.apetrei.engine.renderer.ImageLoader;

import java.awt.image.BufferedImage;

/*!
 * O componenta metita sa reprezinte obiecte statice, si care nu are fuctionalitate de rotatie ca sa salveze resurse.
 */
public class BackgroundSprite extends Component {
    private TransformComponent transformComponent;
    private float spriteScale = 1f;

    //Numele imagini folosite
    String name;
    BufferedImage sprite = null;
    //TODO: BufferedImage is a buffered streaming implementation. To serialize,
    // the data must be flushed out to a static object like a byte[] array and then THAT object may be serialized/deserialized
    //private BufferedImage sprite;

    //Rata de miscare a obictului in relatie cu miscarea camerei, folosit pentru efectul de paralax
    private float scrollFactor = 1f;



    public BackgroundSprite(String name){
        super();
        this.name = name;

        try {
            sprite = ImageLoader.getInstance().getSprite(name);

        } catch ( SpriteNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void componentInit() {

        try {
            transformComponent = (TransformComponent) parent.getComponent(TransformComponent.class);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void componentUpdate( double fT) {
    }

    @Override
    public void componentRender( ) {

        //Aici se afiseaza imaginea pe ecran
        this.getParent().getGameContainer().getRenderer().getLayerRenderer().drawSprite(transformComponent.getPosition(),spriteScale,sprite, scrollFactor);
    }

    //__________________SETTERS______________________

    public void setSpriteScale(float spriteScale) {
        this.spriteScale = spriteScale;
    }

    public void setScrollFactor(float scrollFactor) {
        this.scrollFactor = scrollFactor;
    }

}
