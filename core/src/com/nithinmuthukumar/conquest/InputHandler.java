package com.nithinmuthukumar.conquest;

import com.badlogic.ashley.signals.Listener;
import com.badlogic.ashley.signals.Signal;
import com.badlogic.gdx.InputProcessor;

public class InputHandler implements InputProcessor {
    private Signal<Integer> keyDownSignal=new Signal<>();
    private Signal<Integer> keyUpSignal=new Signal<>();
    private Signal<Character> keyTypedSignal=new Signal<>();
    private Signal<int[]> touchDownSignal=new Signal<>();
    private Signal<int[]> touchUpSignal=new Signal<>();
    private Signal<int[]> touchDraggedSignal=new Signal<>();
    private Signal<int[]> mouseMovedSignal=new Signal();
    private Signal<Integer> scrolledSignal=new Signal();

    public void addListener(String signal, Listener listener){
        switch (signal){
            case "keyDown":
                keyDownSignal.add(listener);
                break;
            case "keyUp":
                keyUpSignal.add(listener);
                break;
            case "keyTyped":
                keyTypedSignal.add(listener);
                break;
            case "touchDown":
                touchDownSignal.add(listener);
                break;
            case "touchUp":
                touchUpSignal.add(listener);
                break;
            case "touchDragged":
                touchDraggedSignal.add(listener);
                break;
            case "mouseMoved":
                mouseMovedSignal.add(listener);
                break;
            case "scrolled":
                scrolledSignal.add(listener);
                break;
        }

    }

    public void removeListener(String signal, Listener listener) {
        switch (signal) {
            case "keyDown":
                keyDownSignal.remove(listener);
                break;
            case "keyUp":
                keyUpSignal.remove(listener);
                break;
            case "keyTyped":
                keyTypedSignal.remove(listener);
                break;
            case "touchDown":
                touchDownSignal.remove(listener);
                break;
            case "touchUp":
                touchUpSignal.remove(listener);
                break;
            case "touchDragged":
                touchDraggedSignal.remove(listener);
                break;
            case "mouseMoved":
                mouseMovedSignal.remove(listener);
                break;
            case "scrolled":
                scrolledSignal.remove(listener);
        }

    }
    @Override
    public boolean keyDown(int keycode) {
        keyDownSignal.dispatch(keycode);
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        keyUpSignal.dispatch(keycode);
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        keyTypedSignal.dispatch(character);
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        touchDownSignal.dispatch(new int[]{screenX,screenY,pointer,button});
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        touchUpSignal.dispatch(new int[]{screenX,screenY,pointer,button});
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        touchDraggedSignal.dispatch(new int[]{screenX,screenY,pointer});
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        mouseMovedSignal.dispatch(new int[]{screenX,screenY});
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        scrolledSignal.dispatch(amount);
        return false;
    }
}
