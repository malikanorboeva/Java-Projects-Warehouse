# Warehouse Management System

A Java-based simulation of a warehouse management system featuring concurrent workers (Wichtel and Elf) managing pallet storage and retrieval operations. This implementation focuses on thread safety and efficient concurrent operations.

## Project Structure

### Main Packages
- `main.goods`: Pallet-related classes
  - `Pallet.java`: Abstract base class for all storable items
  - `Solid.java`: Implementation for solid goods
  - `Substance.java`: Abstract class for substances
  
- `main.stock`: Worker and storage implementations
  - `Stack.java`: Thread-safe implementation of pallet stacks
  - `Elf.java`: Implementation of retrieval worker
  - `Wichtel.java`: Implementation of storage worker
  - `IStockWorker.java`: Interface defining worker behavior
  - `Accountant.java`: Interface for stack access control

- `main.gui`: Visualization components
  - `StockSimulator.java`: Main GUI visualization
  - `ElfController.java`: Controls Elf worker behavior
  - `WichtelController.java`: Controls Wichtel worker behavior
  - `Helper.java`: Base class for workers

- `main.manager`: System coordination
  - `StockManager.java`: Main system coordinator
  - `IncomingPalletManager.java`: Manages incoming pallets

### Key Features
- Thread-safe concurrent operations
- Deadlock prevention system
- Visual simulation interface
- Dynamic pallet storage and retrieval
- Automated worker pathfinding

## Setup and Execution

1. Compile the project:
```bash
javac main/manager/StockManager.java
```

2. Run the simulation:
```bash
java main.manager.StockManager
```

## Worker Behavior

### Elf (Retrieval Worker)
- Retrieves requested pallets from stacks
- Delivers pallets to the outgoing zone
- Handles temporary storage of wrong pallets
- Uses pathfinding to navigate efficiently

### Wichtel (Storage Worker)
- Picks up pallets from the conveyor
- Finds suitable storage locations
- Stacks pallets according to size constraints
- Maintains efficient storage organization

## Technical Implementation

### Thread Safety
- Uses ReentrantLock for stack operations
- CopyOnWriteArrayList for worker management
- AtomicInteger for score tracking
- Volatile fields for shared state

### Concurrency Features
- Deadlock prevention in stack operations
- Thread-safe collection implementations
- Synchronized access to shared resources
- Proper resource cleanup

### Storage Rules
- Maximum 3 pallets per stack
- Smaller pallets must be placed on larger ones
- Thread-safe stack operations
- Proper locking mechanisms

## Visual Interface
- Real-time visualization of warehouse operations
- Color-coded worker representation
- Stack status display
- Score tracking
- Pallet information display

## Known Limitations
- Fixed warehouse dimensions
- Limited worker types
- Predefined storage rules

## System Requirements
- Java Runtime Environment (JRE) 8 or higher
- Minimum 2GB RAM
- Graphics support for GUI
