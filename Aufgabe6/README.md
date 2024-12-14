# Warehouse Goods Management System

## Overview
This Java-based Warehouse Goods Management System handles various types of goods storage and management. The system supports three types of goods:
- Liquid goods (e.g., milk, chemicals) in standardized 90x90x90cm containers
- Solid goods (e.g., bulk materials) in cubic packages of varying sizes
- Unit goods (e.g., boxes of clothing) stacked on 90x90cm pallets

## Features

### General Functionality
- Unique ID generation for all pallets
- Automatic timestamp handling for incoming and outgoing goods
- Support for cooled and non-cooled storage
- Weight and value calculations for all goods types
- CSV file-based data import

### Specific Storage Types

#### Liquid Storage
- Standard container size: 90x90x90cm
- Fill level tracking
- Density-based weight calculation
- Support for hazardous materials (Kemler number and UN number)
- Volume-based value calculation

#### Solid Storage
- Configurable cube sizes (30cm, 60cm, or 90cm)
- Density-based weight calculation
- Support for hazardous materials
- Volume-based value calculation

#### Unit Storage
- Standard pallet base: 90x90cm
- Multiple layer support
- Pieces per layer tracking
- Total weight and value calculations based on individual piece properties

## Project Structure

```
main/
├── goods/
│   ├── Pallet.java         # Abstract base class for all storage types
│   ├── Liquid.java         # Liquid goods implementation
│   ├── Solid.java          # Solid goods implementation
│   └── Unit.java           # Unit goods implementation
└── manager/
    └── PalletManager.java  # Main management and file handling class
```

## Usage

### Data Import Format
The system expects a CSV file named `inputdata.csv` with the following format:

For Liquids (L):
```
L;Description;Cooling;Price;Duration;Density;KemlerNumber;UNNumber
```

For Solids (S):
```
S;Description;Cooling;Price;Duration;Density;KemlerNumber;UNNumber;EdgeLength
```

For Units (U):
```
U;Description;Cooling;Price;Duration;PieceHeight;PieceWeight;NumberOfLayers;PiecesPerLayer
```

### Running the System
1. Place your `inputdata.csv` file in the project root directory
2. Run the `PalletManager` class
3. The system will load all pallets and display their information

## Technical Details

### Weight Calculations
- Liquids: `volume (L) * density (g/L) * fillLevel / 1000 (g to kg)`
- Solids: `volume (L) * density (g/L) / 1000 (g to kg)`
- Units: `totalPieces * weightPerPiece / 1000 (g to kg)`

### Value Calculations
- Liquids: `pricePerLiter * volume * fillLevel`
- Solids: `pricePerLiter * volume`
- Units: `pricePerPiece * totalPieces`

## Requirements
- Java 8 or higher
- CSV file with proper formatting

## Error Handling
The system includes comprehensive error handling for:
- Malformed CSV files
- Invalid numeric values
- Missing data fields
- Unknown pallet types
- File access issues

## Limitations
- Fixed container dimensions for liquids (90x90x90cm)
- Fixed pallet base dimensions for units (90x90cm)
- No support for partial layer removal in unit storage
- Single file input only

## Future Improvements
- Database integration
- GUI interface
- Real-time monitoring
- Multiple file support
- Advanced search functionality
- Stock level warnings
