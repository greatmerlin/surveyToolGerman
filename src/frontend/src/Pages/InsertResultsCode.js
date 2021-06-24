import React from "react";
import Footer from "../Components/Footer";
import InsertCode from "../Components/InsertCode";

/**
 * Fallback page when receiving no or an invalid uuid for evaluation.
 *
 * @returns JSX.Element
 */

export default function InsertResultsCode() {
  return (
    <div>
      <InsertCode
        url="result"
        text="Geben Sie den Code ein, um die Auswertung Ihrer Umfrage zu sehen"
      />
      <Footer />
    </div>
  );
}
