// Holds registered ConversionModule instances and provides them sorted
// alphabetically by name for the Tab Host (ADR-001).

package unitconverter.app

import unitconverter.core.ConversionModule

class ModuleRegistry(modules: List<ConversionModule>) {
    val modules: List<ConversionModule> = modules.sortedBy { it.name }
}
