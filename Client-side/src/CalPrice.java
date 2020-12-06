public class CalPrice {
    private double price;
    private double priceColor=1.75, priceBlack=0.75;


    public double calPrice(int numberOfpages, String options){
        if (options == "color"){
            price = numberOfpages * priceColor;
        }
        else if (options == "black"){
            price = numberOfpages * priceBlack;
        }
        return price;
    }
    public void setColorPrice(double price){
        this.priceColor = price;

    }
    public double getColorPrice(){
        return  this.priceColor;
    }
    public void setBlackPrice(double price){
        this.priceBlack = price;

    }
    public double getBlackPrice(){
        return  this.priceBlack;
    }
    public double getPrice(){
        return this.price;
    }
}
