# Interactive Polynomial Interpolater

## Overview
This application provides an interactive graphical interface for polynomial interpolation, allowing users to place points on a Cartesian plane and automatically generate a polynomial function that passes through all points. The system calculates the coefficients of the polynomial and visualizes the resulting curve in real-time.

## Features
- **Interactive Point Placement**: Click to place points anywhere on the coordinate system
- **Dynamic Polynomial Fitting**: Right-click to generate a polynomial that passes through all placed points
- **Real-time Visualization**: See both your points and the fitted curve on a well-scaled coordinate system
- **Mathematical Equation Display**: View the exact polynomial equation with coefficients
- **Responsive UI**: Clear button to reset and start over

## Technology Stack
- **Java**: Core programming language
- **JavaFX**: Framework for the graphical user interface
- **EJML (Efficient Java Matrix Library)**: For solving the linear system in polynomial interpolation

## How It Works
1. The application uses a Vandermonde matrix system to find polynomial coefficients
2. For n points, it creates a polynomial of degree n-1 (guaranteeing a unique solution)
3. The linear system is solved using efficient matrix operations provided by EJML
4. The resulting polynomial is displayed both graphically and as a mathematical equation

## Mathematical Foundation
The system solves for the coefficients of a polynomial P(x) = a₀ + a₁x + a₂x² + ... + aₙxⁿ by:
1. Constructing a Vandermonde matrix from the x-coordinates
2. Setting up a linear system where each point (xᵢ, yᵢ) must satisfy P(xᵢ) = yᵢ
3. Solving the system to find the coefficient vector [a₀, a₁, a₂, ..., aₙ]

## Usage
1. Left-click to place points on the coordinate plane
2. Right-click to generate the interpolating polynomial
3. The equation will appear at the bottom of the screen
4. Click "Clear" to reset the canvas and start over

## Example Outputs
![Example with 4 points](https://github.com/vivek-ramadhar/Interactive-Polynomial-Interpolater/assets/47376625/a83d3ae1-48ee-4a1a-b41e-c38d992bb51d)

![Example with 5 points](https://github.com/vivek-ramadhar/Interactive-Polynomial-Interpolater/assets/47376625/5155dbcd-cc65-46a3-b3a9-cf0850cfd9f6)

![Complex curve example](https://github.com/vivek-ramadhar/Interactive-Polynomial-Interpolater/assets/47376625/5889a644-2061-40a4-9d16-e410444e9dd9)

## Applications
This tool is useful for:
- Understanding polynomial interpolation concepts
- Generating smooth curves through discrete data points
- Demonstrating numerical methods in action
- Educational purposes in computational mathematics

## Future Improvements
- Support for different interpolation methods (splines, Lagrange, etc.)
- Export functionality for the generated functions
- Data import from CSV or other file formats
- Ability to adjust individual points after placement
- Extended visualization options (zoom, pan, grid adjustment)

## Installation
```bash
# Clone the repository
git clone https://github.com/yourusername/polynomial-interpolater.git

# Navigate to the project directory
cd polynomial-interpolater

# Compile the Java files
javac -cp .:ejml-core.jar:ejml-ddense.jar *.java

# Run the application
java -cp .:ejml-core.jar:ejml-ddense.jar CartesianPlot
```

## Requirements
- Java JDK 8 or higher
- JavaFX (included in JDK 8, separate package for JDK 11+)
- EJML library (Efficient Java Matrix Library)

