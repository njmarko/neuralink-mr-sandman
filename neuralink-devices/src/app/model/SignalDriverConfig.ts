export interface SignalDriverConfig {
    deviceId: string;
    signalType: string;
    low: number;
    high: number;
    frequency: number;
    running: boolean;
};