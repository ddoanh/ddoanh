package byow.Core;

/**
 * @Source: https://bladecast.pro/unity-tutorial/how-to-procedurally-generate-a-dungeon-bsp-method-unity-tilemap#bspSlideShow
 *
 * Start with an initial World size of 80 x 50, that is the current Container class.
 * Has the Tile coordinate where the current container is stored.
 * Has the Tile gridSize, which is the width x height.
 * Has center, which is stored to later connect the rooms, generated inside containers, together.
 *
 */
public class Container {
    int x; int y; int w; int h;
    Tiles center;
    Container left = null;
    Container right = null;

    public Container(int x, int y, int w, int h){
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        center = new Tiles (x + (w/2), y + (h/2));
//        left = null;
//        right = null;
    }

    public class Tiles {
        int x;
        int y;
        Tiles (int x, int y){
            this.x = x;
            this.y = y;
        }
    }

}
