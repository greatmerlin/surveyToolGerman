import React from "react";

/**
 * Displays a bar chart in the context of the full survey.
 *
 * @param {value} number, total votes for this answer.
 * @param {max} number, maximum number of votes for any answer in the survey for context.
 * @returns JSX.Element
 */

export default function BarChart({ value, max }) {
  const getPercentage = () => {
    if (max === 0) {
      return 0;
    }
    const percentage = (value / max) * 100;

    return Math.max(0, Math.min(100, percentage));
  };

  return (
    <span
      style={{
        width: `${getPercentage()}%`,
      }}
      className="bar-chart"
    ></span>
  );
}
