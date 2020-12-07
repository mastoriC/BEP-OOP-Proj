public class Randomizer {
    public String randStr() {
        final int cap = 8;
        String alphaNumStr = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789" + "abcdefghijklmnopqrstuvxyz";
        StringBuilder sb = new StringBuilder(cap);

        for (int i=0; i<cap; i++) {
            int index = (int) (alphaNumStr.length() * Math.random());
            sb.append(alphaNumStr.charAt(index));
        }

        return sb.toString();
    }
}
