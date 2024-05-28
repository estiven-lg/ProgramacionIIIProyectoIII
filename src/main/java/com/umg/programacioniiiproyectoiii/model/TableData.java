package com.umg.programacioniiiproyectoiii.model;

public class TableData {

    private TableData left, right, up, down;
    private int idX, idY;
    private String value;

    private TableData(String value, int idX, int idY) {
        this.value = value;
        this.idX = idX;
        this.idY = idY;
        this.left = null;
        this.right = null;
        this.up = null;
        this.down = null;
    }

    public TableData() {
        this.value = null;
        this.idX = -1;
        this.idY = -1;
        this.left = null;
        this.right = null;
        this.up = null;
        this.down = null;
    }

    public TableData getLeft() {
        return left;
    }

    public void setLeft(TableData left) {
        this.left = left;
    }

    public TableData getRight() {
        return right;
    }

    public void setRight(TableData right) {
        this.right = right;
    }

    public TableData getUp() {
        return up;
    }

    public void setUp(TableData up) {
        this.up = up;
    }

    public TableData getDown() {
        return down;
    }

    public void setDown(TableData down) {
        this.down = down;
    }

    public String getValue() {
        return this.value;
    }

    public int getIdX() {
        return idX;
    }

    public int getIdY() {
        return idY;
    }

    /**
     * search left to right a node with the id X equals to XId
     *
     * @param XId id in X of node to search
     * @return the node with id x equal XId or null if are not found
     */
    private TableData getXById(int XId) {
        TableData node = this;

        while (node.getRight() != null && node.getIdX() < XId) {
            node = node.getRight();
        }

        if (XId == node.getIdX()) {
            return node;
        }

        return null;
    }

    /**
     * search up to down a node with the id Y equals to YId
     *
     * @param XId id in Y of node to search
     * @return the node with id Y equal IdY or null if are not found
     */
    private TableData getYById(int YId) {
        TableData node = this;

        while (node.getDown() != null && node.getIdY() < YId) {
            node = node.getDown();
        }

        if (YId == node.getIdY()) {
            return node;
        }

        return null;
    }

    /**
     * insert a node basing ourselves idx and link left and right nodes
     *
     * @param node node to insert
     */
    private void insertX(TableData node) {
        TableData leftNode = this;
        TableData rightNode = this.getRight();

        while (leftNode.getRight() != null && rightNode.getIdX() < node.idX) {
            leftNode = rightNode;
            rightNode = rightNode.getRight();
        }

        leftNode.setRight(node);
        node.setLeft(leftNode);

        if (rightNode != null) {
            node.setRight(rightNode);
            rightNode.setLeft(node);
        }

    }

    /**
     * insert a node basing ourselves idy and link up and down nodes
     *
     * @param node node to insert
     */
    private void insertY(TableData node) {
        TableData upNode = this;
        TableData downNode = this.getDown();

        while (downNode != null && downNode.getIdY() < node.idY) {
            upNode = downNode;
            downNode = downNode.getDown();
        }

        upNode.setDown(node);
        node.setUp(upNode);

        if (downNode != null) {
            node.setDown(downNode);
            downNode.setUp(node);
        }
    }

    /**
     * remove the node that idX be equal to node param and update the left and
     * right nodes
     *
     * @param node node to remove
     */
    private void removeX(TableData node) {
        if (node.getRight() != null) {
            node.getLeft().setRight(node.getRight());
            node.getRight().setLeft(node.getLeft());
        } else {
            node.getLeft().setRight(null);
        }
    }

    private void removeY(TableData node) {
        if (node.getDown() != null) {
            node.getUp().setDown(node.getDown());
            node.getDown().setUp(node.getUp());
        } else {
            node.getUp().setDown(null);
        }
    }

    /**
     * Insert a new value in a especific position
     *
     * @param value value to insert
     * @param x posion in x axis
     * @param y posion in y axis
     */
    public void insert(String value, int x, int y) {
        TableData newNode = new TableData(value, x, y);

        if (this.getYById(y) == null) {
            this.insertY(new TableData(null, -1, y));
        }
        this.getYById(y).insertX(newNode);

        if (this.getXById(x) == null) {
            this.insertX(new TableData(null, x, -1));
        }
        this.getXById(x).insertY(newNode);
    }

    /**
     * remove a value in a especific position
     *
     * @param x posion in x axis
     * @param y posion in y axis
     */
    public void remove(int x, int y) {
        TableData deletedNode = this.getXById(x).getYById(y);

        this.getYById(y).removeX(deletedNode);
        if (this.getYById(y).getRight() == null) {
            this.removeY(this.getYById(y));
        }

        this.getXById(x).removeY(deletedNode);
        if (this.getXById(x).getDown() == null) {
            this.removeX(this.getXById(x));
        }

    }

    /**
     * get a node in a specific position
     *
     * @param x posion in x axis
     * @param y posion in y axis
     * @return
     */
    public TableData get(int x, int y) {
        try {
            return this.getXById(x).getYById(y);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String toString() {

        if (this.idX == -1 && this.idY == -1) {
            return ("[i,j]");
        }

        if (this.idX == -1) {
            return ("[i," + this.idY + "]");
        }

        if (this.idY == -1) {
            return ("[" + this.idX + ",j]");
        }

        return ("(" + this.idX + "," + this.idY + ")");

    }

    /**
     * show a representation of Ortagonal node
     *
     * @return texto to print in console
     */
    public String toStringR() {
        TableData y = this;
        String text = "";

        while (y != null) {
            TableData x = this;
            while (x != null) {
                if (this.get(x.getIdX(), y.getIdY()) != null) {
                    text += this.get(x.getIdX(), y.getIdY()) + "-";
                } else {
                    text += "------";
                }
                x = x.getRight();
            }

            x = this;
            text += "\n";

            while (x != null) {

                text += "  |   ";

                x = x.getRight();
            }

            text += "\n";

            y = y.getDown();
        }

        return text;
    }

    public String resolve(int x, int y) {
        if (this.get(x, y) == null) {
            return "";
        }

        if (!this.get(x, y).getValue().startsWith("=")) {
            return this.get(x, y).getValue();
        }

        return "Error";

    }
}
