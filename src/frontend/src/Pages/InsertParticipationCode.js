import React from "react";
import InsertCode from "../Components/InsertCode";
import Footer from "../Components/Footer";

/**
 * Fallback page when receiving no or an invalid uuid for participation.
 *
 * @returns JSX.Element
 */

export default function InsertParticipationCode() {
  return (
    <div>
      <InsertCode url="participation" text="Geben Sie den Teilnahmecode ein" />
      <Footer />
    </div>
  );
}
