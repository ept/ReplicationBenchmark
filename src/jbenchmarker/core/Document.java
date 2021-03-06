/**
 *   This file is part of ReplicationBenchmark.
 *
 *   ReplicationBenchmark is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   ReplicationBenchmark is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with ReplicationBenchmark.  If not, see <http://www.gnu.org/licenses/>.
 *
 **/

package jbenchmarker.core;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author urso
 */
public abstract class Document {
    

    public Document() {
    }
    
    /* 
     * View of the document (without metadata)
     */
    abstract public String view();
    
    /**
     * Applies an operation
     */ 
    public void apply(Operation op) {
        applyLocal(op);
    }
    
    abstract protected void applyLocal(Operation op);
}
