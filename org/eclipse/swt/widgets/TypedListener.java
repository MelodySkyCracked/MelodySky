/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.widgets;

import org.eclipse.swt.events.ArmEvent;
import org.eclipse.swt.events.ArmListener;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.DragDetectEvent;
import org.eclipse.swt.events.DragDetectListener;
import org.eclipse.swt.events.ExpandEvent;
import org.eclipse.swt.events.ExpandListener;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.GestureEvent;
import org.eclipse.swt.events.GestureListener;
import org.eclipse.swt.events.HelpEvent;
import org.eclipse.swt.events.HelpListener;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MenuDetectEvent;
import org.eclipse.swt.events.MenuDetectListener;
import org.eclipse.swt.events.MenuEvent;
import org.eclipse.swt.events.MenuListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.MouseTrackListener;
import org.eclipse.swt.events.MouseWheelListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SegmentEvent;
import org.eclipse.swt.events.SegmentListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.events.ShellListener;
import org.eclipse.swt.events.TouchEvent;
import org.eclipse.swt.events.TouchListener;
import org.eclipse.swt.events.TraverseEvent;
import org.eclipse.swt.events.TraverseListener;
import org.eclipse.swt.events.TreeEvent;
import org.eclipse.swt.events.TreeListener;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.internal.SWTEventListener;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

public class TypedListener
implements Listener {
    protected SWTEventListener eventListener;

    public TypedListener(SWTEventListener sWTEventListener) {
        this.eventListener = sWTEventListener;
    }

    public SWTEventListener getEventListener() {
        return this.eventListener;
    }

    @Override
    public void handleEvent(Event event) {
        switch (event.type) {
            case 26: {
                ((ShellListener)this.eventListener).shellActivated(new ShellEvent(event));
                break;
            }
            case 30: {
                ((ArmListener)this.eventListener).widgetArmed(new ArmEvent(event));
                break;
            }
            case 21: {
                ShellEvent shellEvent = new ShellEvent(event);
                ((ShellListener)this.eventListener).shellClosed(shellEvent);
                event.doit = shellEvent.doit;
                break;
            }
            case 18: {
                if (this.eventListener instanceof TreeListener) {
                    ((TreeListener)this.eventListener).treeCollapsed(new TreeEvent(event));
                    break;
                }
                ((ExpandListener)this.eventListener).itemCollapsed(new ExpandEvent(event));
                break;
            }
            case 27: {
                ((ShellListener)this.eventListener).shellDeactivated(new ShellEvent(event));
                break;
            }
            case 20: {
                ((ShellListener)this.eventListener).shellDeiconified(new ShellEvent(event));
                break;
            }
            case 14: {
                ((SelectionListener)this.eventListener).widgetDefaultSelected(new SelectionEvent(event));
                break;
            }
            case 12: {
                ((DisposeListener)this.eventListener).widgetDisposed(new DisposeEvent(event));
                break;
            }
            case 29: {
                ((DragDetectListener)this.eventListener).dragDetected(new DragDetectEvent(event));
                break;
            }
            case 17: {
                if (this.eventListener instanceof TreeListener) {
                    ((TreeListener)this.eventListener).treeExpanded(new TreeEvent(event));
                    break;
                }
                ((ExpandListener)this.eventListener).itemExpanded(new ExpandEvent(event));
                break;
            }
            case 15: {
                ((FocusListener)this.eventListener).focusGained(new FocusEvent(event));
                break;
            }
            case 16: {
                ((FocusListener)this.eventListener).focusLost(new FocusEvent(event));
                break;
            }
            case 48: {
                GestureEvent gestureEvent = new GestureEvent(event);
                ((GestureListener)this.eventListener).gesture(gestureEvent);
                event.doit = gestureEvent.doit;
                break;
            }
            case 28: {
                ((HelpListener)this.eventListener).helpRequested(new HelpEvent(event));
                break;
            }
            case 23: {
                ((MenuListener)this.eventListener).menuHidden(new MenuEvent(event));
                break;
            }
            case 19: {
                ((ShellListener)this.eventListener).shellIconified(new ShellEvent(event));
                break;
            }
            case 1: {
                KeyEvent keyEvent = new KeyEvent(event);
                ((KeyListener)this.eventListener).keyPressed(keyEvent);
                event.doit = keyEvent.doit;
                break;
            }
            case 2: {
                KeyEvent keyEvent = new KeyEvent(event);
                ((KeyListener)this.eventListener).keyReleased(keyEvent);
                event.doit = keyEvent.doit;
                break;
            }
            case 24: {
                ((ModifyListener)this.eventListener).modifyText(new ModifyEvent(event));
                break;
            }
            case 35: {
                MenuDetectEvent menuDetectEvent = new MenuDetectEvent(event);
                ((MenuDetectListener)this.eventListener).menuDetected(menuDetectEvent);
                event.x = menuDetectEvent.x;
                event.y = menuDetectEvent.y;
                event.doit = menuDetectEvent.doit;
                event.detail = menuDetectEvent.detail;
                break;
            }
            case 3: {
                ((MouseListener)this.eventListener).mouseDown(new MouseEvent(event));
                break;
            }
            case 8: {
                ((MouseListener)this.eventListener).mouseDoubleClick(new MouseEvent(event));
                break;
            }
            case 6: {
                ((MouseTrackListener)this.eventListener).mouseEnter(new MouseEvent(event));
                break;
            }
            case 7: {
                ((MouseTrackListener)this.eventListener).mouseExit(new MouseEvent(event));
                break;
            }
            case 32: {
                ((MouseTrackListener)this.eventListener).mouseHover(new MouseEvent(event));
                break;
            }
            case 5: {
                ((MouseMoveListener)this.eventListener).mouseMove(new MouseEvent(event));
                return;
            }
            case 37: {
                ((MouseWheelListener)this.eventListener).mouseScrolled(new MouseEvent(event));
                return;
            }
            case 4: {
                ((MouseListener)this.eventListener).mouseUp(new MouseEvent(event));
                break;
            }
            case 10: {
                ((ControlListener)this.eventListener).controlMoved(new ControlEvent(event));
                break;
            }
            case 9: {
                PaintEvent paintEvent = new PaintEvent(event);
                ((PaintListener)this.eventListener).paintControl(paintEvent);
                event.gc = paintEvent.gc;
                break;
            }
            case 11: {
                ((ControlListener)this.eventListener).controlResized(new ControlEvent(event));
                break;
            }
            case 49: {
                SegmentEvent segmentEvent = new SegmentEvent(event);
                ((SegmentListener)this.eventListener).getSegments(segmentEvent);
                event.segments = segmentEvent.segments;
                event.segmentsChars = segmentEvent.segmentsChars;
                break;
            }
            case 13: {
                SelectionEvent selectionEvent = new SelectionEvent(event);
                ((SelectionListener)this.eventListener).widgetSelected(selectionEvent);
                event.x = selectionEvent.x;
                event.y = selectionEvent.y;
                event.doit = selectionEvent.doit;
                break;
            }
            case 22: {
                ((MenuListener)this.eventListener).menuShown(new MenuEvent(event));
                break;
            }
            case 47: {
                ((TouchListener)this.eventListener).touch(new TouchEvent(event));
                break;
            }
            case 31: {
                TraverseEvent traverseEvent = new TraverseEvent(event);
                ((TraverseListener)this.eventListener).keyTraversed(traverseEvent);
                event.detail = traverseEvent.detail;
                event.doit = traverseEvent.doit;
                break;
            }
            case 25: {
                VerifyEvent verifyEvent = new VerifyEvent(event);
                ((VerifyListener)this.eventListener).verifyText(verifyEvent);
                event.text = verifyEvent.text;
                event.doit = verifyEvent.doit;
                break;
            }
        }
    }
}

