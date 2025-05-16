package adduct;
import java.util.Map;
public class Adduct {

    /**
     * Calculate the mass to search depending on the adduct hypothesis
     *
     * @param mz mz
     * @param adduct adduct name ([M+H]+, [2M+H]+, [M+2H]2+, etc..)
     * @return the monoisotopic mass of the experimental mass mz with the adduct @param adduct
     */
    public static Double getMonoisotopicMassFromMZ(Double mz, String adduct) {
        Double correction = getAdductCorrection(adduct);
        if (correction == null) return null;

        int charge = getAdductCharge(adduct);
        return mz * charge + correction;
    }


    /**
     * Calculate the mz of a monoisotopic mass with the corresponding adduct
     *
     * @param monoMass
     * @param adduct adduct name ([M+H]+, [2M+H]+, [M+2H]2+, etc..)
     * @return m/z
     */
    public static Double getMZFromMonoisotopicMass(Double monoMass, String adduct) {
        Double correction = getAdductCorrection(adduct);
        if (correction == null) return null;

        int charge = getAdductCharge(adduct);
        return (monoMass - correction) / charge;
    }

    /**
     * Returns the ppm difference between measured mass and theoretical mass
     *
     * @param experimentalMass    Mass measured by MS
     * @param theoreticalMass Theoretical mass of the compound
     */
    public static int calculatePPMIncrement(Double experimentalMass, Double theoreticalMass) {
        int ppmIncrement;
        ppmIncrement = (int) Math.round(Math.abs((experimentalMass - theoreticalMass) * 1000000
                / theoreticalMass));
        return ppmIncrement;
    }

    /**
     * Returns the ppm difference between measured mass and theoretical mass
     *
     * @param measuredMass    Mass measured by MS
     * @param ppm ppm of tolerance
     */
    public static double calculateDeltaPPM(Double experimentalMass, int ppm) {
        double deltaPPM;
        deltaPPM =  Math.round(Math.abs((experimentalMass * ppm) / 1000000));
        return deltaPPM;

    }

    // Restituisce la correzione dell’adduct, cercando prima tra i positivi, poi tra i negativi
    private static Double getAdductCorrection(String adduct) {
        Double correction = AdductList.MAPMZPOSITIVEADDUCTS.get(adduct);
        if (correction == null) {
            correction = AdductList.MAPMZNEGATIVEADDUCTS.get(adduct);
        }
        return correction;
    }

    // Determina la carica dell’adduct: 2 se contiene "2+", "2-", o "2−", altrimenti 1
    private static int getAdductCharge(String adduct) {
        if (adduct.contains("2+") || adduct.contains("2-") || adduct.contains("2−")) {
            return 2;
        }
        return 1;
    }



}
