package com.zyz.dangxia.bigdata;

import com.zyz.dangxia.common.task.PriceSection;

public class PriceSectionUtil {
    public static PriceSection getSection(int p) {
        switch (p) {
            case 1:
                return new PriceSection(0, 20);
            case 2:
                return new PriceSection(20, 50);
            case 3:
                return new PriceSection(50, 80);
            case 4:
                return new PriceSection(80, 100);
            case 5:
                return new PriceSection(100, 150);
            case 6:
                return new PriceSection(150, 250);
            case 7:
                return new PriceSection(250, 500);
            case 8:
                return new PriceSection(500, 1000);
            case 9:
                return new PriceSection(1000, 2000);
            default:
                return new PriceSection(0, 20);
        }
    }

    public static int getP(double price) {
        if (0 <= price && price <= 20) {
            return 1;
        } else if (20 < price && price <= 50) {
            return 2;
        } else if (50 < price && price <= 80) {
            return 3;
        } else if (80 < price && price <= 100) {
            return 4;
        } else if (100 < price && price <= 150) {
            return 5;
        } else if (150 < price && price <= 250) {
            return 6;
        } else if (250 < price && price <= 500) {
            return 7;
        } else if (500 < price && price <= 1000) {
            return 8;
        } else if (1000 < price && price <= 2000) {
            return 9;
        } else return 2;
    }
}
