/***************************************************************************************************
 * BROWSER POLYFILLS
 */
(window as any).global = window; // 👈 global 설정

/***************************************************************************************************
 * APPLICATION IMPORTS
 */

import 'zone.js';
import { Buffer } from 'buffer';
(window as any).Buffer = Buffer;

import * as process from 'process';
(window as any).process = process;
