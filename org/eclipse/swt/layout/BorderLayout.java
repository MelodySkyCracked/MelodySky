/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.layout;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.BorderData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Layout;

public class BorderLayout
extends Layout {
    private static final String LAYOUT_KEY = BorderLayout.class.getName() + ".layoutData";
    private static final ToIntFunction WIDTH = BorderLayout::lambda$static$6;
    private static final ToIntFunction HEIGHT = BorderLayout::lambda$static$7;
    public int type = 256;
    public int marginWidth = 0;
    public int marginHeight = 0;
    public int spacing = 0;
    public int controlSpacing = 0;
    public double widthDistributionFactor = 0.5;
    public double heightDistributionFactor = 0.5;

    @Override
    protected Point computeSize(Composite composite, int n, int n2, boolean bl) {
        int n3;
        int n4;
        int n5;
        int n6;
        int n7;
        int n8;
        int n9;
        if (n2 > -1 && n > -1) {
            return new Point(n, n2);
        }
        Stream<Map.Entry> stream = Arrays.stream(composite.getChildren()).map(arg_0 -> this.lambda$computeSize$0(bl, arg_0));
        Map<Integer, List<Map.Entry>> map = stream.collect(Collectors.groupingBy(BorderLayout::region));
        if (n <= -1) {
            IntStream.Builder builder = IntStream.builder();
            int n10 = this.getTotal(WIDTH, 128, map);
            n9 = this.getTotal(WIDTH, 1024, map);
            n8 = this.type == 256 ? this.getTotal(WIDTH, 0x1000000, map) : BorderLayout.getMax(WIDTH, 0x1000000, map);
            n7 = BorderLayout.getMax(WIDTH, 16384, map);
            n6 = BorderLayout.getMax(WIDTH, 131072, map);
            n5 = n7 + n8 + n6;
            if (n8 > 0) {
                if (n7 > 0) {
                    n5 += this.spacing;
                }
                if (n6 > 0) {
                    n5 += this.spacing;
                }
            } else if (n7 > 0 && n6 > 0) {
                n5 += this.spacing;
            }
            builder.add(n5);
            builder.add(n10);
            builder.add(n9);
            n4 = builder.build().max().orElse(0) + 2 * this.marginWidth;
        } else {
            n4 = n;
        }
        if (n2 <= -1) {
            IntStream.Builder builder = IntStream.builder();
            n9 = BorderLayout.getMax(HEIGHT, 128, map);
            n8 = BorderLayout.getMax(HEIGHT, 1024, map);
            n7 = this.getTotal(HEIGHT, 16384, map);
            n6 = this.getTotal(HEIGHT, 131072, map);
            n5 = this.type == 256 ? BorderLayout.getMax(HEIGHT, 0x1000000, map) : this.getTotal(HEIGHT, 0x1000000, map);
            if (n5 > 0) {
                if (n9 > 0) {
                    n5 += this.spacing;
                }
                if (n8 > 0) {
                    n5 += this.spacing;
                }
            }
            if (n7 > 0) {
                if (n9 > 0) {
                    n7 += this.spacing;
                }
                if (n8 > 0) {
                    n7 += this.spacing;
                }
            }
            if (n6 > 0) {
                if (n9 > 0) {
                    n6 += this.spacing;
                }
                if (n8 > 0) {
                    n6 += this.spacing;
                }
            }
            int n11 = n9 + n8;
            builder.add(n7 + n11);
            builder.add(n5 + n11);
            builder.add(n6 + n11);
            n3 = builder.build().max().orElse(0) + 2 * this.marginHeight;
        } else {
            n3 = n2;
        }
        return new Point(n4, n3);
    }

    private int getTotal(ToIntFunction toIntFunction, int n, Map map) {
        List list = map.getOrDefault(n, Collections.emptyList());
        if (list.isEmpty()) {
            return 0;
        }
        return list.stream().mapToInt(arg_0 -> BorderLayout.lambda$getTotal$1(toIntFunction, arg_0)).sum() + (list.size() - 1) * this.controlSpacing;
    }

    private static int getMax(ToIntFunction toIntFunction, int n, Map map) {
        List list = map.getOrDefault(n, Collections.emptyList());
        return BorderLayout.getMax(toIntFunction, list, -1, -1, false);
    }

    private static int getMax(ToIntFunction toIntFunction, List list, int n, int n2, boolean bl) {
        if (list.isEmpty()) {
            return 0;
        }
        if (n != -1 || n2 != -1) {
            return list.stream().mapToInt(arg_0 -> BorderLayout.lambda$getMax$2(toIntFunction, n, n2, bl, arg_0)).max().orElse(0);
        }
        return list.stream().mapToInt(arg_0 -> BorderLayout.lambda$getMax$3(toIntFunction, arg_0)).max().orElse(0);
    }

    @Override
    protected void layout(Composite composite, boolean bl) {
        int n;
        int n2;
        int n3;
        int n4;
        int n5;
        Rectangle rectangle = composite.getClientArea();
        int n6 = rectangle.x + this.marginWidth;
        int n7 = rectangle.y + this.marginHeight;
        int n8 = rectangle.width - 2 * this.marginWidth;
        int n9 = rectangle.height - 2 * this.marginHeight;
        Stream<Map.Entry> stream = Arrays.stream(composite.getChildren()).map(arg_0 -> this.lambda$layout$4(bl, arg_0));
        Map<Integer, List<Map.Entry>> map = stream.collect(Collectors.groupingBy(BorderLayout::region));
        Map.Entry entry2 = null;
        map.getOrDefault(0, Collections.emptyList()).forEach(arg_0 -> BorderLayout.lambda$layout$5(n6, n7, arg_0));
        List list = map.getOrDefault(128, Collections.emptyList());
        List list2 = map.getOrDefault(1024, Collections.emptyList());
        List list3 = map.getOrDefault(16384, Collections.emptyList());
        List list4 = map.getOrDefault(131072, Collections.emptyList());
        List list5 = map.getOrDefault(0x1000000, Collections.emptyList());
        int n10 = list.size();
        int n11 = n10 > 0 ? (n8 - (n10 - 1) * this.controlSpacing) / n10 : 0;
        int n12 = BorderLayout.getMax(HEIGHT, list, n11, -1, bl);
        int n13 = list2.size();
        int n14 = n13 > 0 ? (n8 - (n13 - 1) * this.controlSpacing) / n13 : 0;
        int n15 = BorderLayout.getMax(HEIGHT, list2, n14, -1, bl);
        if (n12 + n15 > n9) {
            n5 = (int)((double)n9 * this.heightDistributionFactor);
            if (n12 > n5) {
                n12 = n5;
            }
            n15 = n9 - n12;
        }
        n5 = n9 - n12 - n15;
        int n16 = list3.size();
        int n17 = BorderLayout.getMax(WIDTH, list3, -1, -1, bl);
        int n18 = list4.size();
        int n19 = BorderLayout.getMax(WIDTH, list4, -1, -1, bl);
        if (n17 + n19 > n8) {
            n4 = (int)((double)n8 * this.widthDistributionFactor);
            if (n17 > n4) {
                n17 = n4;
            }
            n19 = n8 - n17;
        }
        n4 = n8 - n17 - n19;
        int n20 = list5.size();
        if (n10 > 0) {
            n3 = n6;
            n2 = n7;
            for (Map.Entry entry2 : list) {
                ((Control)entry2.getKey()).setBounds(n3, n2, n11, n12);
                n3 += n11 + this.controlSpacing;
            }
        }
        if (n13 > 0) {
            n3 = n6;
            n2 = n7 + n5 + n12;
            for (Map.Entry entry2 : list2) {
                ((Control)entry2.getKey()).setBounds(n3, n2, n14, n15);
                n3 += n14 + this.controlSpacing;
            }
        }
        if (n16 > 0) {
            n3 = n6;
            n2 = n7 + n12;
            int n21 = n9 - n12 - n15;
            if (n10 > 0) {
                n2 += this.spacing;
                n21 -= this.spacing;
            }
            if (n13 > 0) {
                n21 -= this.spacing;
            }
            n = (n21 - (n16 - 1) * this.controlSpacing) / n16;
            for (Map.Entry entry3 : list3) {
                ((Control)entry3.getKey()).setBounds(n3, n2, n17, n);
                n2 += n + this.controlSpacing;
            }
        }
        if (n18 > 0) {
            n3 = n6 + n4 + n17;
            n2 = n7 + n12;
            int n22 = n9 - n12 - n15;
            if (n10 > 0) {
                n2 += this.spacing;
                n22 -= this.spacing;
            }
            if (n13 > 0) {
                n22 -= this.spacing;
            }
            n = (n22 - (n18 - 1) * this.controlSpacing) / n18;
            for (Map.Entry entry3 : list4) {
                ((Control)entry3.getKey()).setBounds(n3, n2, n19, n);
                n2 += n + this.controlSpacing;
            }
        }
        if (n20 > 0) {
            int n23;
            int n24;
            n3 = n6 + n17;
            n2 = n7 + n12;
            int n25 = n5;
            n = n4;
            if (n16 > 0) {
                n3 += this.spacing;
                n -= this.spacing;
            }
            if (n18 > 0) {
                n -= this.spacing;
            }
            if (n10 > 0) {
                n2 += this.spacing;
                n25 -= this.spacing;
            }
            if (n13 > 0) {
                n25 -= this.spacing;
            }
            if (this.type == 256) {
                n24 = n25;
                n23 = (n - (n20 - 1) * this.controlSpacing) / n20;
            } else {
                n23 = n;
                n24 = (n25 - (n20 - 1) * this.controlSpacing) / n20;
            }
            for (Map.Entry entry4 : list5) {
                ((Control)entry4.getKey()).setBounds(n3, n2, n23, n24);
                if (this.type == 256) {
                    n3 += n23 + this.controlSpacing;
                    continue;
                }
                n2 += n24 + this.controlSpacing;
            }
        }
    }

    private Map.Entry borderDataControl(Control control, boolean bl) {
        BorderData borderData;
        Object object = control.getLayoutData();
        if (object instanceof BorderData) {
            BorderData borderData2 = (BorderData)object;
            if (bl) {
                borderData2.flushCache(control);
            }
            return new AbstractMap.SimpleEntry<Control, BorderData>(control, borderData2);
        }
        BorderData borderData3 = borderData = bl ? null : (BorderData)control.getData(LAYOUT_KEY);
        if (borderData == null) {
            borderData = new BorderData();
            control.setData(LAYOUT_KEY, borderData);
        }
        return new AbstractMap.SimpleEntry<Control, BorderData>(control, borderData);
    }

    private static int region(Map.Entry entry) {
        BorderData borderData = (BorderData)entry.getValue();
        if (borderData == null) {
            return 0x1000000;
        }
        return borderData.getRegion();
    }

    public String toString() {
        return "BorderLayout [type=" + (this.type == 256 ? "SWT.HORIZONTAL" : "SWT.VERTICAL") + ", marginWidth=" + this.marginWidth + ", marginHeight=" + this.marginHeight + ", spacing=" + this.spacing + ", controlSpacing=" + this.controlSpacing + ", widthDistributionFactor=" + this.widthDistributionFactor + ", heightDistributionFactor=" + this.heightDistributionFactor;
    }

    private static int lambda$static$7(Point point) {
        return point.y;
    }

    private static int lambda$static$6(Point point) {
        return point.x;
    }

    private static void lambda$layout$5(int n, int n2, Map.Entry entry) {
        ((Control)entry.getKey()).setBounds(n, n2, 0, 0);
    }

    private Map.Entry lambda$layout$4(boolean bl, Control control) {
        return this.borderDataControl(control, bl);
    }

    private static int lambda$getMax$3(ToIntFunction toIntFunction, Map.Entry entry) {
        return toIntFunction.applyAsInt(((BorderData)entry.getValue()).getSize((Control)entry.getKey()));
    }

    private static int lambda$getMax$2(ToIntFunction toIntFunction, int n, int n2, boolean bl, Map.Entry entry) {
        return toIntFunction.applyAsInt(((BorderData)entry.getValue()).computeSize((Control)entry.getKey(), n, n2, bl));
    }

    private static int lambda$getTotal$1(ToIntFunction toIntFunction, Map.Entry entry) {
        return toIntFunction.applyAsInt(((BorderData)entry.getValue()).getSize((Control)entry.getKey()));
    }

    private Map.Entry lambda$computeSize$0(boolean bl, Control control) {
        return this.borderDataControl(control, bl);
    }
}

