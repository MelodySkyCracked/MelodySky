/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.dnd;

import org.eclipse.swt.dnd.DNDEvent;
import org.eclipse.swt.dnd.DragSource;
import org.eclipse.swt.dnd.DragSourceEffect;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.DragSourceListener;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.DropTargetEffect;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.DropTargetListener;
import org.eclipse.swt.internal.SWTEventListener;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.TypedListener;
import org.eclipse.swt.widgets.Widget;

class DNDListener
extends TypedListener {
    Widget dndWidget;

    DNDListener(SWTEventListener sWTEventListener) {
        super(sWTEventListener);
    }

    @Override
    public void handleEvent(Event event) {
        switch (event.type) {
            case 2008: {
                DragSourceEvent dragSourceEvent = new DragSourceEvent((DNDEvent)event);
                DragSource dragSource = (DragSource)this.dndWidget;
                DragSourceEffect dragSourceEffect = dragSource.getDragSourceEffect();
                ((DragSourceListener)this.eventListener).dragStart(dragSourceEvent);
                if (dragSourceEvent.doit && dragSource.canBeginDrag() && dragSourceEffect != null) {
                    dragSourceEffect.dragStart(dragSourceEvent);
                }
                dragSourceEvent.updateEvent((DNDEvent)event);
                break;
            }
            case 2000: {
                DragSourceEvent dragSourceEvent = new DragSourceEvent((DNDEvent)event);
                DragSourceEffect dragSourceEffect = ((DragSource)this.dndWidget).getDragSourceEffect();
                if (dragSourceEffect != null) {
                    dragSourceEffect.dragFinished(dragSourceEvent);
                }
                ((DragSourceListener)this.eventListener).dragFinished(dragSourceEvent);
                dragSourceEvent.updateEvent((DNDEvent)event);
                break;
            }
            case 2001: {
                DragSourceEvent dragSourceEvent = new DragSourceEvent((DNDEvent)event);
                DragSourceEffect dragSourceEffect = ((DragSource)this.dndWidget).getDragSourceEffect();
                if (dragSourceEffect != null) {
                    dragSourceEffect.dragSetData(dragSourceEvent);
                }
                ((DragSourceListener)this.eventListener).dragSetData(dragSourceEvent);
                dragSourceEvent.updateEvent((DNDEvent)event);
                break;
            }
            case 2002: {
                DropTargetEvent dropTargetEvent = new DropTargetEvent((DNDEvent)event);
                ((DropTargetListener)this.eventListener).dragEnter(dropTargetEvent);
                DropTargetEffect dropTargetEffect = ((DropTarget)this.dndWidget).getDropTargetEffect();
                if (dropTargetEffect != null) {
                    dropTargetEffect.dragEnter(dropTargetEvent);
                }
                dropTargetEvent.updateEvent((DNDEvent)event);
                break;
            }
            case 2003: {
                DropTargetEvent dropTargetEvent = new DropTargetEvent((DNDEvent)event);
                ((DropTargetListener)this.eventListener).dragLeave(dropTargetEvent);
                DropTargetEffect dropTargetEffect = ((DropTarget)this.dndWidget).getDropTargetEffect();
                if (dropTargetEffect != null) {
                    dropTargetEffect.dragLeave(dropTargetEvent);
                }
                dropTargetEvent.updateEvent((DNDEvent)event);
                break;
            }
            case 2004: {
                DropTargetEvent dropTargetEvent = new DropTargetEvent((DNDEvent)event);
                ((DropTargetListener)this.eventListener).dragOver(dropTargetEvent);
                DropTargetEffect dropTargetEffect = ((DropTarget)this.dndWidget).getDropTargetEffect();
                if (dropTargetEffect != null) {
                    dropTargetEffect.dragOver(dropTargetEvent);
                }
                dropTargetEvent.updateEvent((DNDEvent)event);
                break;
            }
            case 2006: {
                DropTargetEvent dropTargetEvent = new DropTargetEvent((DNDEvent)event);
                ((DropTargetListener)this.eventListener).drop(dropTargetEvent);
                DropTargetEffect dropTargetEffect = ((DropTarget)this.dndWidget).getDropTargetEffect();
                if (dropTargetEffect != null) {
                    dropTargetEffect.drop(dropTargetEvent);
                }
                dropTargetEvent.updateEvent((DNDEvent)event);
                break;
            }
            case 2007: {
                DropTargetEvent dropTargetEvent = new DropTargetEvent((DNDEvent)event);
                ((DropTargetListener)this.eventListener).dropAccept(dropTargetEvent);
                DropTargetEffect dropTargetEffect = ((DropTarget)this.dndWidget).getDropTargetEffect();
                if (dropTargetEffect != null) {
                    dropTargetEffect.dropAccept(dropTargetEvent);
                }
                dropTargetEvent.updateEvent((DNDEvent)event);
                break;
            }
            case 2005: {
                DropTargetEvent dropTargetEvent = new DropTargetEvent((DNDEvent)event);
                ((DropTargetListener)this.eventListener).dragOperationChanged(dropTargetEvent);
                DropTargetEffect dropTargetEffect = ((DropTarget)this.dndWidget).getDropTargetEffect();
                if (dropTargetEffect != null) {
                    dropTargetEffect.dragOperationChanged(dropTargetEvent);
                }
                dropTargetEvent.updateEvent((DNDEvent)event);
                break;
            }
        }
    }
}

