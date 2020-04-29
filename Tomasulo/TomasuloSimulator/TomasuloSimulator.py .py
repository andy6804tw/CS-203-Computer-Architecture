# -*- coding: utf-8 -*-
import logging

logging.basicConfig(level=logging.DEBUG)

# INSS = [
#     'LD F6,8',
#     'LD F2,40',
#     'MULTD F0,F2,F4',
#     'SUBD F8,F6,F2',
#     'DIVD F10,F0,F6',
#     'ADDD F6,F8,F2'
# ]
INSS = [
    'LD F6,8',
    'LD F2,40',
    'ADDD F4,F2,F6',
    'DIVD F8,F0,F4',
    'SUBD F6,F6,F2',
    'MULTD F10,F6,F4',
    # 'ST F10,40'
]


class TomasuloSimulator(object):
    INSCONFIG = {
        'ADDD': {
            'time': 2,
            'resv': 'ADD'
        },
        'SUBD': {
            'time': 2,
            'resv': 'ADD'
        },
        'MULTD': {
            'time': 10,
            'resv': 'MUL'
        },
        'DIVD': {
            'time': 40,
            'resv': 'MUL'
        },
        'LD': {
            'time': 2,
            'resv': 'LOAD'
        },
        'ST': {
            'time': 2,
            'resv': 'STORE'
        },
    }
    RESVNUMCONFIG = {
        'ADD': 3,
        'MUL': 2,
        'LOAD': 2,
        'STORE': 2
    }
    FN = 20
    RN = 20
    MEMSIZE = 4096

    def __init__(self, inss):
        self.inss = self.load_inss(inss)
        self.fns = [['VAL', 0] for t in range(self.FN)]
        self.rns = [0 for t in range(self.RN)]
        self.reserve_stations = {
            'ADD': [
                {
                    'time': 0,
                    'busy': False,
                    'op': '',
                    's1': ['', 0],
                    's2': ['', 0],
                    'rank': t,
                    'insr': 0
                }
                for t in range(self.RESVNUMCONFIG['ADD'])],
            'MUL': [
                {
                    'time': 0,
                    'busy': False,
                    'op': '',
                    's1': ['', 0],
                    's2': ['', 0],
                    'rank': t,
                    'insr': 0
                }
                for t in range(self.RESVNUMCONFIG['MUL'])],
            'LOAD': [
                {
                    'time': 0,
                    'busy': False,
                    'addr': 0,
                    'rank': t,
                    'insr': 0
                }
                for t in range(self.RESVNUMCONFIG['LOAD'])],
            'STORE': [
                {
                    'time': 0,
                    'busy': False,
                    's': ['', 0],
                    'addr': 0,
                    'rank': t,
                    'insr': 0
                }
                for t in range(self.RESVNUMCONFIG['STORE'])],
        }
        self.insnum = len(self.inss)
        self.mem = [0 for t in range(self.MEMSIZE)]
        self.pc = 0
        self.iter = 0

    def step(self):
        terminate_list = []
        self.iter += 1

        if self.pc == len(self.inss):
            pass
        else:
            instr = self.inss[self.pc]
            instr_rs = self.reserve_stations[instr['resv']]
            t = -1
            for t in range(len(instr_rs)):
                if not instr_rs[t]['busy']:
                    break
            if t == -1:
                pass
            else:
                self.reserve_stations[instr['resv']][t]['time'] = instr['time']
                self.reserve_stations[instr['resv']][t]['busy'] = True
                self.reserve_stations[instr['resv']][t]['insr'] = self.pc
                self.inss[self.pc]['state']['issue'] = self.iter
                terminate_list.append(self.pc)
                if instr['resv'] in ['ADD', 'MUL']:
                    self.reserve_stations[instr['resv']][t]['op'] = instr['name']
                    self.reserve_stations[instr['resv']][t]['s1'], _ = self._parseReg(instr['nums'][1])
                    self.reserve_stations[instr['resv']][t]['s2'], _ = self._parseReg(instr['nums'][2])
                    _, targett = self._parseReg(instr['nums'][0])
                    self.fns[targett] = [instr['resv'] + str(t), 0]
                elif instr['resv'] == 'LOAD':
                    self.reserve_stations[instr['resv']][t]['addr'] = int(instr['nums'][1])
                    _, targett = self._parseReg(instr['nums'][0])
                    self.fns[targett] = [instr['resv'] + str(t), 0]
                    print(instr['nums'][1])
                elif instr['resv'] == 'STORE':
                    self.reserve_stations[instr['resv']][t]['s'], _ = self._parseReg(instr['nums'][0])
                    self.reserve_stations[instr['resv']][t]['addr'], _ = int(instr['nums'][1])
                self.pc += 1

        for name, resv in self.reserve_stations.items():
            for t in range(len(resv)):
                resv1 = self.reserve_stations[name][t]
                if resv1['insr'] not in terminate_list and resv1['busy']:
                    if name in ['ADD', 'MUL']:
                        if resv1['s1'][0] == 'VAL' and resv1['s2'][0] == 'VAL':
                            self.reserve_stations[name][t]['time'] -= 1
                    else:
                        self.reserve_stations[name][t]['time'] -= 1
                    if self.reserve_stations[name][t]['time'] == 0:
                        self.inss[resv1['insr']]['state']['exec'] = self.iter
                        terminate_list.append(resv1['insr'])
                    if self.reserve_stations[name][t]['time'] < 0:
                        self.reserve_stations[name][t]['time'] = 0

        for name, resv in self.reserve_stations.items():
            for t in range(len(resv)):
                resv1 = self.reserve_stations[name][t]
                if resv1['insr'] not in terminate_list and resv1['busy'] and resv1['time'] == 0:
                    self.reserve_stations[name][t]['busy'] = False
                    res = 0
                    self.inss[resv1['insr']]['state']['write'] = self.iter
                    if name in ['ADD', 'MUL']:
                        assert resv1['s1'][0] == 'VAL'
                        assert resv1['s2'][0] == 'VAL'
                        res = 0
                        s1 = resv1['s1'][1]
                        s2 = resv1['s2'][1]
                        iname = self.inss[resv1['insr']]['name']
                        if iname == 'ADDD':
                            res = s1 + s2
                        elif iname == 'SUBD':
                            res = s1 - s2
                        elif iname == 'MULD':
                            res = s1 * s2
                        elif iname == 'DIVD':
                            try:
                                res = s1 / s2
                            except ZeroDivisionError:
                                res = 0.0
                    elif name == 'LOAD':
                        res = self.mem[resv1['addr']]
                    elif name == 'STORE':
                        assert resv1['s'][0] == 'VAL'
                        self.mem[resv1['addr']] = resv1['s'][1]
                    if name in ['ADD', 'MUL', 'LOAD']:
                        self._broadcast(name, resv1['rank'], res)

    def stepn(self):
        pass

    def run(self):
        while not self._checkFinish():
            self.step()
            logging.debug([ins['state'] for ins in self.inss])

    def load_inss(self, inss):
        return [self._load_ins(ins) for ins in inss]

    def _load_ins(self, ins):
        ins = ins.split(' ')
        insname = ins[0]
        insnums = ins[1].split(',')
        logging.debug(insnums)
        tdict = {
            'name': insname,
            'nums': insnums,
            'time': self.INSCONFIG[insname]['time'],
            'resv': self.INSCONFIG[insname]['resv'],
            'state': {
                'issue': 0,
                'exec': 0,
                'write': 0
            }
        }
        return tdict

    def _broadcast(self, vname, vrank, val):
        tname = vname + str(vrank)
        for name, resv in self.reserve_stations.items():
            for t in range(len(resv)):
                resv1 = self.reserve_stations[name][t]
                if name in ['ADD', 'MUL']:
                    if resv1['s1'][0] == tname:
                        self.reserve_stations[name][t]['s1'][0] = 'VAL'
                        self.reserve_stations[name][t]['s1'][1] = val
                    if resv1['s2'][0] == tname:
                        self.reserve_stations[name][t]['s2'][0] = 'VAL'
                        self.reserve_stations[name][t]['s2'][1] = val
                elif name in ['LOAD', 'STORE']:
                    pass
        for t in range(len(self.fns)):
            if self.fns[t][0] == tname:
                self.fns[t][0] = 'VAL'
                self.fns[t][1] = val

    def _parseReg(self, regstr):
        if regstr[0] == 'F':
            return self.fns[int(regstr[1:])], int(regstr[1:])
        else:
            assert regstr[0] == 'R'
            return self.rns[int(regstr[1:])], int(regstr[1:])

    def _checkFinish(self):
        for ins in self.inss:
            if ins['state']['write'] == 0:
                return False
        return True


if __name__ == '__main__':
    tms = TomasuloSimulator(INSS)
    tms.run()
    logging.debug(tms.fns)
    logging.debug(tms.rns)