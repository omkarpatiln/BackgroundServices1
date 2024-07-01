import { View, NativeEventEmitter, NativeModules } from 'react-native';
import React, { useEffect, useState } from 'react';

const { BackgroundServiceCheck } = NativeModules;

interface BackGroundServicesInterFace {
  Title: string;
  Text: string;
  onEachSecond: number;
  onServiceRunning?: () => void;
  IsServiceRunning?: (boolean: boolean) => void;
  killService: boolean;
}

const BackGroundServices: React.FC<BackGroundServicesInterFace> = ({
  Title,
  Text,
  onEachSecond,
  killService,
  IsServiceRunning,
}) => {
  const [isServiceRunning, setIsServiceRunning] = useState(false);
  const [ServiceContent, setServiceContent] = useState({
    Title,
    Text,
  });

  const RunTheFunction = async () => {
    if (IsServiceRunning) {
      const status = await BackgroundServiceCheck.isBackgroundServiceRunning();
      IsServiceRunning(status);
      setIsServiceRunning(status);
    }
    console.log('check');
  };

  useEffect(() => {
    RunTheFunction();
  }, [isServiceRunning]);

  useEffect(() => {
    if (killService) {
      console.log('Services Stopped');
      BackgroundServiceCheck.stopBackgroundService();
      if (IsServiceRunning) {
        IsServiceRunning(false);
      }
    } else {
      const eventEmitter = new NativeEventEmitter(BackgroundServiceCheck);
      if (BackgroundServiceCheck) {
        console.log('Starting service');
        BackgroundServiceCheck.startBackgroundService(onEachSecond * 1000, ServiceContent);
        eventEmitter.addListener('backgroundServiceTriggered', RunTheFunction);
      } else {
        console.log('Native module not exist');
      }
    }
  }, [killService]);

  return <View />;
};

export default BackGroundServices;
export type { BackGroundServicesInterFace };
