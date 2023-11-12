/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.custom;

import org.eclipse.swt.custom.BidiSegmentEvent;
import org.eclipse.swt.custom.BidiSegmentListener;
import org.eclipse.swt.custom.CaretEvent;
import org.eclipse.swt.custom.CaretListener;
import org.eclipse.swt.custom.ExtendedModifyEvent;
import org.eclipse.swt.custom.ExtendedModifyListener;
import org.eclipse.swt.custom.LineBackgroundEvent;
import org.eclipse.swt.custom.LineBackgroundListener;
import org.eclipse.swt.custom.LineStyleEvent;
import org.eclipse.swt.custom.LineStyleListener;
import org.eclipse.swt.custom.MovementEvent;
import org.eclipse.swt.custom.MovementListener;
import org.eclipse.swt.custom.PaintObjectEvent;
import org.eclipse.swt.custom.PaintObjectListener;
import org.eclipse.swt.custom.StyledTextContent;
import org.eclipse.swt.custom.StyledTextEvent;
import org.eclipse.swt.custom.TextChangeListener;
import org.eclipse.swt.custom.TextChangedEvent;
import org.eclipse.swt.custom.TextChangingEvent;
import org.eclipse.swt.custom.VerifyKeyListener;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.internal.SWTEventListener;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.TypedListener;

class StyledTextListener
extends TypedListener {
    StyledTextListener(SWTEventListener sWTEventListener) {
        super(sWTEventListener);
    }

    @Override
    public void handleEvent(Event event) {
        switch (event.type) {
            case 3000: {
                ExtendedModifyEvent extendedModifyEvent = new ExtendedModifyEvent((StyledTextEvent)event);
                ((ExtendedModifyListener)this.eventListener).modifyText(extendedModifyEvent);
                break;
            }
            case 3001: {
                LineBackgroundEvent lineBackgroundEvent = new LineBackgroundEvent((StyledTextEvent)event);
                ((LineBackgroundListener)this.eventListener).lineGetBackground(lineBackgroundEvent);
                ((StyledTextEvent)event).lineBackground = lineBackgroundEvent.lineBackground;
                break;
            }
            case 3007: {
                BidiSegmentEvent bidiSegmentEvent = new BidiSegmentEvent((StyledTextEvent)event);
                ((BidiSegmentListener)this.eventListener).lineGetSegments(bidiSegmentEvent);
                ((StyledTextEvent)event).segments = bidiSegmentEvent.segments;
                ((StyledTextEvent)event).segmentsChars = bidiSegmentEvent.segmentsChars;
                break;
            }
            case 3002: {
                LineStyleEvent lineStyleEvent = new LineStyleEvent((StyledTextEvent)event);
                ((LineStyleListener)this.eventListener).lineGetStyle(lineStyleEvent);
                ((StyledTextEvent)event).ranges = lineStyleEvent.ranges;
                ((StyledTextEvent)event).styles = lineStyleEvent.styles;
                ((StyledTextEvent)event).alignment = lineStyleEvent.alignment;
                ((StyledTextEvent)event).indent = lineStyleEvent.indent;
                ((StyledTextEvent)event).verticalIndent = lineStyleEvent.verticalIndent;
                ((StyledTextEvent)event).wrapIndent = lineStyleEvent.wrapIndent;
                ((StyledTextEvent)event).justify = lineStyleEvent.justify;
                ((StyledTextEvent)event).bullet = lineStyleEvent.bullet;
                ((StyledTextEvent)event).bulletIndex = lineStyleEvent.bulletIndex;
                ((StyledTextEvent)event).tabStops = lineStyleEvent.tabStops;
                break;
            }
            case 3008: {
                PaintObjectEvent paintObjectEvent = new PaintObjectEvent((StyledTextEvent)event);
                ((PaintObjectListener)this.eventListener).paintObject(paintObjectEvent);
                break;
            }
            case 3005: {
                VerifyEvent verifyEvent = new VerifyEvent(event);
                ((VerifyKeyListener)this.eventListener).verifyKey(verifyEvent);
                event.doit = verifyEvent.doit;
                break;
            }
            case 3006: {
                TextChangedEvent textChangedEvent = new TextChangedEvent((StyledTextContent)event.data);
                ((TextChangeListener)this.eventListener).textChanged(textChangedEvent);
                break;
            }
            case 3003: {
                TextChangingEvent textChangingEvent = new TextChangingEvent((StyledTextContent)event.data, (StyledTextEvent)event);
                ((TextChangeListener)this.eventListener).textChanging(textChangingEvent);
                break;
            }
            case 3004: {
                TextChangedEvent textChangedEvent = new TextChangedEvent((StyledTextContent)event.data);
                ((TextChangeListener)this.eventListener).textSet(textChangedEvent);
                break;
            }
            case 3009: {
                MovementEvent movementEvent = new MovementEvent((StyledTextEvent)event);
                ((MovementListener)this.eventListener).getNextOffset(movementEvent);
                ((StyledTextEvent)event).end = movementEvent.newOffset;
                break;
            }
            case 3010: {
                MovementEvent movementEvent = new MovementEvent((StyledTextEvent)event);
                ((MovementListener)this.eventListener).getPreviousOffset(movementEvent);
                ((StyledTextEvent)event).end = movementEvent.newOffset;
                break;
            }
            case 3011: {
                CaretEvent caretEvent = new CaretEvent((StyledTextEvent)event);
                ((CaretListener)this.eventListener).caretMoved(caretEvent);
                ((StyledTextEvent)event).end = caretEvent.caretOffset;
                break;
            }
        }
    }
}

