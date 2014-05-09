package cn.sluk3r.luImpl.document;

/**
 * Created by wangxc on 2014/5/9.
 */
public class Field {
    public Field(String name, String value, Store store, Index index) {
    }

    /** Specifies whether and how a field should be stored. */
    public static enum Store {

        /** Store the original field value in the index. This is useful for short texts
         * like a document's title which should be displayed with the results. The
         * value is stored in its original form, i.e. no analyzer is used before it is
         * stored.
         */
        YES {
            @Override
            public boolean isStored() { return true; }
        },

        /** Do not store the field value in the index. */
        NO {
            @Override
            public boolean isStored() { return false; }
        };

        public abstract boolean isStored();
    }

    /** Specifies whether and how a field should be indexed. */
    public static enum Index {

        /** Do not index the field value. This field can thus not be searched,
         * but one can still access its contents provided it is
         * {@link Field.Store stored}. */
        NO {
            @Override
            public boolean isIndexed()  { return false; }
            @Override
            public boolean isAnalyzed() { return false; }
            @Override
            public boolean omitNorms()  { return true;  }
        },

        /** Index the tokens produced by running the field's
         * value through an Analyzer.  This is useful for
         * common text. */
        ANALYZED {
            @Override
            public boolean isIndexed()  { return true;  }
            @Override
            public boolean isAnalyzed() { return true;  }
            @Override
            public boolean omitNorms()  { return false; }
        },

        /** Index the field's value without using an Analyzer, so it can be searched.
         * As no analyzer is used the value will be stored as a single term. This is
         * useful for unique Ids like product numbers.
         */
        NOT_ANALYZED {
            @Override
            public boolean isIndexed()  { return true;  }
            @Override
            public boolean isAnalyzed() { return false; }
            @Override
            public boolean omitNorms()  { return false; }
        },

        /** Expert: Index the field's value without an Analyzer,
         * and also disable the storing of norms.  Note that you
         * can also separately enable/disable norms by calling
         * {@link Field#setOmitNorms}.  No norms means that
         * index-time field and document boosting and field
         * length normalization are disabled.  The benefit is
         * less memory usage as norms take up one byte of RAM
         * per indexed field for every document in the index,
         * during searching.  Note that once you index a given
         * field <i>with</i> norms enabled, disabling norms will
         * have no effect.  In other words, for this to have the
         * above described effect on a field, all instances of
         * that field must be indexed with NOT_ANALYZED_NO_NORMS
         * from the beginning. */
        NOT_ANALYZED_NO_NORMS {
            @Override
            public boolean isIndexed()  { return true;  }
            @Override
            public boolean isAnalyzed() { return false; }
            @Override
            public boolean omitNorms()  { return true;  }
        },

        /** Expert: Index the tokens produced by running the
         *  field's value through an Analyzer, and also
         *  separately disable the storing of norms.  See
         *  {@link #NOT_ANALYZED_NO_NORMS} for what norms are
         *  and why you may want to disable them. */
        ANALYZED_NO_NORMS {
            @Override
            public boolean isIndexed()  { return true;  }
            @Override
            public boolean isAnalyzed() { return true;  }
            @Override
            public boolean omitNorms()  { return true;  }
        };

        /** Get the best representation of the index given the flags. */
        public static Index toIndex(boolean indexed, boolean analyzed) {
            return toIndex(indexed, analyzed, false);
        }

        /** Expert: Get the best representation of the index given the flags. */
        public static Index toIndex(boolean indexed, boolean analyzed, boolean omitNorms) {

            // If it is not indexed nothing else matters
            if (!indexed) {
                return Index.NO;
            }

            // typical, non-expert
            if (!omitNorms) {
                if (analyzed) {
                    return Index.ANALYZED;
                }
                return Index.NOT_ANALYZED;
            }

            // Expert: Norms omitted
            if (analyzed) {
                return Index.ANALYZED_NO_NORMS;
            }
            return Index.NOT_ANALYZED_NO_NORMS;
        }

        public abstract boolean isIndexed();
        public abstract boolean isAnalyzed();
        public abstract boolean omitNorms();
    }

    /** Specifies whether and how a field should have term vectors. */
    public static enum TermVector {

        /** Do not store term vectors.
         */
        NO {
            @Override
            public boolean isStored()      { return false; }
            @Override
            public boolean withPositions() { return false; }
            @Override
            public boolean withOffsets()   { return false; }
        },

        /** Store the term vectors of each document. A term vector is a list
         * of the document's terms and their number of occurrences in that document. */
        YES {
            @Override
            public boolean isStored()      { return true;  }
            @Override
            public boolean withPositions() { return false; }
            @Override
            public boolean withOffsets()   { return false; }
        },

        /**
         * Store the term vector + token position information
         *
         * @see #YES
         */
        WITH_POSITIONS {
            @Override
            public boolean isStored()      { return true;  }
            @Override
            public boolean withPositions() { return true;  }
            @Override
            public boolean withOffsets()   { return false; }
        },

        /**
         * Store the term vector + Token offset information
         *
         * @see #YES
         */
        WITH_OFFSETS {
            @Override
            public boolean isStored()      { return true;  }
            @Override
            public boolean withPositions() { return false; }
            @Override
            public boolean withOffsets()   { return true;  }
        },

        /**
         * Store the term vector + Token position and offset information
         *
         * @see #YES
         * @see #WITH_POSITIONS
         * @see #WITH_OFFSETS
         */
        WITH_POSITIONS_OFFSETS {
            @Override
            public boolean isStored()      { return true;  }
            @Override
            public boolean withPositions() { return true;  }
            @Override
            public boolean withOffsets()   { return true;  }
        };

        /** Get the best representation of a TermVector given the flags. */
        public static TermVector toTermVector(boolean stored, boolean withOffsets, boolean withPositions) {

            // If it is not stored, nothing else matters.
            if (!stored) {
                return TermVector.NO;
            }

            if (withOffsets) {
                if (withPositions) {
                    return Field.TermVector.WITH_POSITIONS_OFFSETS;
                }
                return Field.TermVector.WITH_OFFSETS;
            }

            if (withPositions) {
                return Field.TermVector.WITH_POSITIONS;
            }
            return Field.TermVector.YES;
        }

        public abstract boolean isStored();
        public abstract boolean withPositions();
        public abstract boolean withOffsets();
    }

}
