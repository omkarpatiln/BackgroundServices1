import React from 'react';
interface BackGroundServicesInterFace {
    Title: string;
    Text: string;
    onEachSecond: number;
    onServiceRunning?: () => void;
    IsServiceRunning?: (boolean: boolean) => void;
    killService: boolean;
}
declare const BackGroundServices: React.FC<BackGroundServicesInterFace>;
export default BackGroundServices;
export type { BackGroundServicesInterFace };
