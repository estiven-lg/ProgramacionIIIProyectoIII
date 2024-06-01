package com.umg.programacioniiiproyectoiii.model;

import com.umg.programacioniiiproyectoiii.controller.Operation;
import com.umg.programacioniiiproyectoiii.controller.Armectic;

public class SheetData {

    private SheetData left, right, up, down;
    private int idX, idY;
    private String value;

    private SheetData(String value, int idX, int idY) {
        this.value = value;
        this.idX = idX;
        this.idY = idY;
        this.left = null;
        this.right = null;
        this.up = null;
        this.down = null;
    }

    public SheetData() {
        this.value = null;
        this.idX = -1;
        this.idY = -1;
        this.left = null;
        this.right = null;
        this.up = null;
        this.down = null;
    }

    public SheetData getLeft() {
        return left;
    }

    public void setLeft(SheetData left) {
        this.left = left;
    }

    public SheetData getRight() {
        return right;
    }

    public void setRight(SheetData right) {
        this.right = right;
    }

    public SheetData getUp() {
        return up;
    }

    public void setUp(SheetData up) {
        this.up = up;
    }

    public SheetData getDown() {
        return down;
    }

    public void setDown(SheetData down) {
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
    private SheetData getXById(int XId) {
        SheetData node = this;

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
    private SheetData getYById(int YId) {
        SheetData node = this;

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
    private void insertX(SheetData node) {
        SheetData leftNode = this;
        SheetData rightNode = this.getRight();

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
    private void insertY(SheetData node) {
        SheetData upNode = this;
        SheetData downNode = this.getDown();

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
    private void removeX(SheetData node) {
        if (node.getRight() != null) {
            node.getLeft().setRight(node.getRight());
            node.getRight().setLeft(node.getLeft());
        } else {
            node.getLeft().setRight(null);
        }
    }

    private void removeY(SheetData node) {
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
        SheetData newNode = new SheetData(value, x, y);

        if (this.getYById(y) == null) {
            this.insertY(new SheetData(null, -1, y));
        }
        this.getYById(y).insertX(newNode);

        if (this.getXById(x) == null) {
            this.insertX(new SheetData(null, x, -1));
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
        SheetData deletedNode = this.getXById(x).getYById(y);

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
    public SheetData get(int x, int y) {
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
        SheetData y = this;
        String text = "";

        while (y != null) {
            SheetData x = this;
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

    public String resolve(int x, int y, boolean returnZero) {
        if (this.get(x, y) == null) {
            return returnZero ? "0" : "";
        }

        if (!this.get(x, y).getValue().startsWith("=")) {
            return this.get(x, y).getValue();
        } else {
            if (Armectic.evalOperation(this.get(x, y).getValue().substring(1)) != null) {
                return "Error de sintaxis";
            } else {
                try {
                    Operation nodeRoot = Operation.createOperation(this.get(x, y).getValue().substring(1), this);
                    return String.valueOf(nodeRoot.resolveOperation());
                } catch (Exception e) {
                    return "Error de sintaxis";

                }
            }
        }
    }
}
