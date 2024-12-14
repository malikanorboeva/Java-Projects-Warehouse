# Warehouse Management System

## Overview
A Java-based warehouse management system that handles the storage and management of pallets in a multi-slot storage facility. The system supports both cooled and non-cooled storage slots, with functionality for pallet placement, removal, and slot management.

## Features
- Pallet Management
  - Unique ID generation for each pallet
  - Customizable dimensions (width, depth, height)
  - Cooling requirements tracking
  - Timestamp tracking for incoming and outgoing pallets
  - Human-readable text representation

- Storage Slot Management
  - Support for cooled and non-cooled storage slots
  - Grid-based storage organization (multiple rows and columns)
  - Validation of cooling requirements during pallet placement
  - Slot status tracking (occupied/free)

- Interactive Operations
  - Create new pallets with custom specifications
  - Place pallets in available slots
  - Remove pallets from slots
  - Swap pallets between slots
  - View detailed storage information

## Project Structure
```
src/
└── main/
    ├── Pallet.java       # Core pallet class with basic attributes and operations
    ├── Slot.java         # Storage slot management and validation
    └── StockManager.java # Main program logic and user interface
```

## Classes

### Pallet
Represents a storage pallet with:
- Unique ID
- Physical dimensions
- Cooling requirements
- Temporal information (arrival and departure times)

### Slot
Represents a storage location with:
- Position coordinates (x, y)
- Cooling capability
- Current pallet status
- Validation logic for pallet placement

### StockManager
Main program class that:
- Manages the storage grid
- Handles user input
- Processes storage operations
- Displays system status

## Usage

### Creating a Pallet
```java
Pallet pallet = Pallet.createPalletFromUserInput();
// or
Pallet pallet = Pallet.createPalletFrom(description, width, depth, height, cooling, duration);
```

### Managing Slots
```java
Slot slot = Slot.createSlotFrom(xpos, ypos, cooling);
boolean success = slot.setPallet(pallet);
slot.release();  // Remove pallet
```

### Running the Program
1. Execute the StockManager class
2. Use the interactive menu to perform operations:
   - 'i' - View slot information
   - 'c' - Create and place new pallet
   - 'r' - Release pallet from slot
   - 's' - Swap pallets between slots
   - 'q' - Quit program

## Input Validation
The system validates:
- Positive dimensions for pallets
- Valid storage coordinates
- Cooling requirements compatibility
- Storage slot availability
- Valid duration periods

## Notes
- All dimensions are in consistent units
- Time durations are in days
- Cooling requirements are strictly enforced
- Slots coordinates start from (0,0)
- Storage has designated cooled and non-cooled sections

## Error Handling
- Invalid input prompts for re-entry
- Cooling requirement mismatches are prevented
- Invalid operations are safely handled with appropriate user feedback
- Array bounds are checked for all operations

## Future Enhancements
Potential areas for expansion:
- Database integration
- Multiple storage zones
- Advanced search capabilities
- Reporting features
- GUI interface
