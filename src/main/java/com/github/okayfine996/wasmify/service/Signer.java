package com.github.okayfine996.wasmify.service;

import java.util.Objects;

public class Signer {
        public String name;
        public String value;

        public Signer() {
        }

        public Signer(String name, String value) {
            this.name = name;
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Signer signer = (Signer) o;
            return Objects.equals(name, signer.name) && Objects.equals(value, signer.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, value);
        }

        @Override
        public String toString() {
            return "Signer{" + "name='" + name + '\'' + ", value='" + value + '\'' + '}';
        }
    }