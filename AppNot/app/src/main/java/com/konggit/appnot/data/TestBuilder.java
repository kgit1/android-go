package com.konggit.appnot.data;

/**
 * Created by erik on 04.10.2017.
 */

public class TestBuilder {

    private int firstValue;
    private int secondValue;
    private String thirdValue;
    private String fourthValue;
    private String fifthValue;
    private String sixthValue;

    public static class Builder {

        private int firstValue;
        private int secondValue;
        private String thirdValue = "0";
        private String fourthValue = "0";
        private String fifthValue = "0";
        private String sixthValue = "0";

        public Builder(int firstValue, int secondValue) {

            this.firstValue = firstValue;
            this.secondValue = secondValue;

        }

        public Builder thirdValue(String thirdValue) {

            this.thirdValue = thirdValue;

            return this;
        }

        public Builder fourthValue(String fourthValue) {

            this.fourthValue = fourthValue;

            return this;
        }

        public Builder fifthValue(String fifthValue) {

            this.fifthValue = fifthValue;

            return this;
        }

        public Builder sixthValue(String sixthValue) {

            this.sixthValue = sixthValue;

            return this;
        }

        public TestBuilder build() {

            return new TestBuilder(this);
        }

    }

    private TestBuilder(Builder builder) {

        firstValue = builder.firstValue;
        secondValue = builder.secondValue;
        thirdValue = builder.thirdValue;
        fourthValue = builder.fourthValue;
        fifthValue = builder.fifthValue;
        sixthValue = builder.sixthValue;

    }

    @Override
    public String toString() {
        return "TestBuilder{" +
                "firstValue=" + firstValue +
                ", secondValue=" + secondValue +
                ", thirdValue='" + thirdValue + '\'' +
                ", fourthValue='" + fourthValue + '\'' +
                ", fifthValue='" + fifthValue + '\'' +
                ", sixthValue='" + sixthValue + '\'' +
                '}';
    }
}
